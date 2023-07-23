package com.smallc.xiwenlejian.recommender.offline.spark.model

import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.spark.ml.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.{col, collect_list, concat_ws, udf}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import redis.clients.jedis.Jedis

import java.io.ObjectOutputStream
import java.util.{ArrayList, List => JList}

object Item2Vec {

  def generateUserBehaviorSeq(userBehavior: DataFrame): DataFrame = {
    // 按user_id分组并按book_id升序排序，生成用户的行为序列
    val userBehaviorSeq = userBehavior
      .groupBy("user_id")
      .agg(collect_list("book_id").as("item_sequence"))
    userBehaviorSeq.show(10)
    userBehaviorSeq
  }

  def generateUserEmbedding(userBehavior: DataFrame, itemEmbedding: DataFrame): DataFrame = {
    // 定义将itemEmbedding计算平均值的UDF函数
    val embeddingMeanUDF = udf((embeddings: Seq[Array[Double]]) => {
      val embeddingSum = embeddings.reduce((v1, v2) => {
        v1.zip(v2).map { case (x, y) => x + y }
      })
      val embeddingMean = embeddingSum.map(_ / embeddings.size)
      embeddingMean
    })
    // 计算用户喜欢的物品的embedding均值
    val userEmbedding = userBehavior
      .join(itemEmbedding, Seq("book_id"), "inner")
      .groupBy("user_id")
      .agg(embeddingMeanUDF(collect_list("item_embedding")).alias("user_embedding"))
    userEmbedding
  }

  def trainItem2Vec(userBehaviorSeq: DataFrame): Word2VecModel = {
    // 训练Item2Vec模型
    val item2Vec = new Word2Vec()
      .setVectorSize(10)
      .setWindowSize(5)
      .setMinCount(1)
      .setMaxIter(200)
      .setInputCol("item_sequence")
      .setOutputCol("item_embedding")
    val model = item2Vec.fit(userBehaviorSeq)
    model
  }

  def vectorToText(vector: Seq[Double]): String = {
    val dimensionValues = vector.zipWithIndex.map { case (value, index) => s"${index + 1}:$value" }
    dimensionValues.mkString(" ")
  }

  // 将DataFrame中的数据写入文本文件
  def writeEmbeddingsToTextFile(dataFrame: DataFrame, outputPath: String): Unit = {
    // 获取DataFrame的列名数组
    val columnNames: Array[String] = dataFrame.columns

    // 定义将embedding向量转换为文本格式的UDF函数
    val vectorToTextUDF = udf((vector: Seq[Double]) => vectorToText(vector))

    // 添加一个新的列，该列包含embedding向量的文本表示
    val dfWithTextEmbeddings = dataFrame.withColumn("text_embedding", vectorToTextUDF(col(columnNames(1))))

    // 将book_id列和转换后的embedding列拼接为一个新的列，中间用空格隔开
    val resultDF = dfWithTextEmbeddings.withColumn("book_id_and_embedding", concat_ws(" ", col(columnNames(0)), col("text_embedding")))

    // 选择新拼接的列，即book_id和转换后的embedding拼接的结果
    val selectedDF = resultDF.select("book_id_and_embedding").coalesce(1)

    // 将DataFrame中的数据写入文本文件
    selectedDF.write.text(outputPath)
  }

  def main(args: Array[String]): Unit = {
    // 创建Spark会话
    val spark = SparkSession.builder()
      .appName("Item2Vec")
      .master("local")
      .getOrCreate()

    // 读取csv文件
    val userBehaviorFilePath = getClass.getResource("/user_behavior.csv").getPath
    // 定义CSV文件的模式
    val schema = StructType(Seq(
      StructField("user_id", StringType, nullable = true),
      StructField("book_id", StringType, nullable = true)
    ))

    // 读取user_behavior.csv文件为DataFrame
    val userBehavior = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "false")
      .schema(schema)
      .load(userBehaviorFilePath)

    // 生成用户行为序列
    val userBehaviorSeq = generateUserBehaviorSeq(userBehavior)
    // 训练模型
    val model = trainItem2Vec(userBehaviorSeq)
    // 获得itemEmbedding
    val vectors = model.getVectors
    // 定义将向量转换为数组的UDF函数
    val vectorToArrayUDF = udf((vector: Vector) => vector.toArray)
    // 将vectors列中的向量转换为数组
    val itemEmbedding = vectors.withColumn("item_embedding", vectorToArrayUDF(col("vector"))).drop("vector").withColumnRenamed("word", "book_id")

    // 生成userEmbedding
    val userEmbedding = generateUserEmbedding(userBehavior, itemEmbedding)

    // 创建一个函数，用于将嵌入向量写入Redis
    def saveEmbeddings(iterator: Iterator[Row], prefix: String, key: String, value: String): Unit = {
      // 创建Jedis实例
      val jedis: Jedis = new Jedis("127.0.0.1", 6379)
      // 遍历每个分区的数据
      iterator.foreach { row =>
        val id = row.getAs[String](key)
        val embeddingSeq = row.getAs[Seq[Double]](value)

        // 将嵌入向量转换为数组
        val embedding = embeddingSeq.toArray

        // 将嵌入向量转换为Java的List
        val embeddingList: JList[Double] = new ArrayList[Double]()
        embedding.foreach(embeddingList.add)

        // 将嵌入向量作为List保存到Redis中
        jedis.set((prefix + id).getBytes, serializeList(embeddingList))
      }
      // 关闭连接
      jedis.close()
    }

    // 序列化List
    def serializeList(list: JList[Double]): Array[Byte] = {
      val bos = new ByteArrayOutputStream()
      val oos = new ObjectOutputStream(bos)
      oos.writeObject(list)
      oos.flush()
      bos.toByteArray
    }

    // 将user_embedding保存到Redis中
    userEmbedding.rdd.foreachPartition(iter => saveEmbeddings(iter, "U", "user_id", "user_embedding"))
    // 将item_embedding保存到Redis中
    itemEmbedding.rdd.foreachPartition(iter => saveEmbeddings(iter, "I", "book_id", "item_embedding"))

    // 将itemEmbedding保存为svm文件
    writeEmbeddingsToTextFile(userEmbedding, "user_embedding.svm")
    writeEmbeddingsToTextFile(itemEmbedding, "item_embedding.svm")
  }

}
