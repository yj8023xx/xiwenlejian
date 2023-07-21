package com.smallc.xiwenlejian.recommender.offline.spark.model

import org.apache.commons.io.output.ByteArrayOutputStream
import org.apache.spark.ml.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.{col, collect_list, expr, sort_array, udf}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.sql.functions._
import redis.clients.jedis.Jedis

import java.io.ObjectOutputStream
import java.util.{ArrayList, List => JList}

object Item2Vec {

  def generateUserBehaviorSeq(sparkSession: SparkSession, ratingFilePath: String): DataFrame = {
    // 定义CSV文件的模式
    val schema = StructType(Seq(
      StructField("user_id", StringType, nullable = true),
      StructField("book_id", StringType, nullable = true),
      StructField("rating", DoubleType, nullable = true)
    ))

    // 读取rating.csv文件为DataFrame
    val ratingDF = sparkSession.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "false")
      .schema(schema)
      .load(ratingFilePath)

    // 按user_id分组并按book_id升序排序，生成用户的行为序列
    val userBehaviorSeq = ratingDF
      .groupBy("user_id")
      .agg(sort_array(collect_list("book_id")).as("item_sequence"))
    userBehaviorSeq.show(10)
    userBehaviorSeq.printSchema()
    userBehaviorSeq
  }

  def generateUserEmbedding(userBehaviorSeq: DataFrame, itemEmbedding: DataFrame): DataFrame = {
    // 定义将itemEmbedding计算平均值的UDF函数
    val embeddingMeanUDF = udf((embeddings: Seq[Array[Double]]) => {
      val embeddingSum = embeddings.reduce((v1, v2) => {
        v1.zip(v2).map { case (x, y) => x + y }
      })
      val embeddingMean = embeddingSum.map(_ / embeddings.size)
      embeddingMean
    })
    // 计算用户喜欢的物品的embedding均值
    val userEmbedding = userBehaviorSeq
      .join(itemEmbedding, expr("array_contains(item_sequence, item_id)")) // 根据item_id是否包含在item_sequence中进行连接
      .groupBy("user_id")
      .agg(embeddingMeanUDF(collect_list("item_embedding")).alias("user_embedding"))
    userEmbedding
  }

  def trainItem2Vec(userBehaviorSeq: DataFrame): Word2VecModel = {
    // 训练Item2Vec模型
    val item2Vec = new Word2Vec().setVectorSize(100).setWindowSize(5).setMinCount(1).setInputCol("item_sequence").setOutputCol("item_embedding")
    val model = item2Vec.fit(userBehaviorSeq)
    model
  }

  def main(args: Array[String]): Unit = {
    // 创建Spark会话
    val sparkSession = SparkSession.builder()
      .appName("item2Vec")
      .master("local")
      .config("spark.sql.objectHashAggregate.sortBased.fallbackThreshold", "1024")
      .getOrCreate()

    val ratingFilePath = getClass.getResource("/rating.csv").getPath
    // 生成用户行为序列
    val userBehaviorSeq = generateUserBehaviorSeq(sparkSession, ratingFilePath)

    // 训练模型
    val model = trainItem2Vec(userBehaviorSeq)
    // 获得itemEmbedding
    val vectors = model.getVectors

    // 定义将向量转换为数组的UDF函数
    val vectorToArrayUDF = udf((vector: Vector) => vector.toArray)
    // 将vectors列中的向量转换为数组
    val itemEmbedding = vectors.withColumn("item_embedding", vectorToArrayUDF(col("vector"))).drop("vector").withColumnRenamed("word", "item_id")
    itemEmbedding.show(10)
    itemEmbedding.printSchema()

    // 生成userEmbedding
    val userEmbedding = generateUserEmbedding(userBehaviorSeq, itemEmbedding)
    userEmbedding.show(10)
    userEmbedding.printSchema()

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
    userEmbedding.rdd.foreachPartition(iter => saveEmbeddings(iter, "u", "user_id", "user_embedding"))
    // 将item_embedding保存到Redis中
    itemEmbedding.rdd.foreachPartition(iter => saveEmbeddings(iter, "i", "item_id", "item_embedding"))
  }

}
