package com.smallc.xiwenlejian.recommender.offline.spark.model

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.SparkSession

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/23
 * @since com.smallc.xiwenlejian.recommender.offline.spark.model
 */
object CollaborativeFiltering {

  def main(args: Array[String]): Unit = {
    // 创建Spark会话
    val spark = SparkSession.builder()
      .appName("ALS Recommendation Model")
      .master("local")
      .getOrCreate()

    // 读取csv文件
    val ratingFilePath = getClass.getResource("/rating.csv").getPath
    val rating = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(ratingFilePath)

    val als = new ALS()
      .setMaxIter(10)
      .setRegParam(0.01)
      .setUserCol("user_id")
      .setItemCol("book_id")
      .setRatingCol("rating")
      .setColdStartStrategy("drop") // 冷启动策略：忽略未知用户或物品，不会对其进行推荐

    val Array(train_data, test_data) = rating.randomSplit(Array(0.8, 0.2))
    val model = als.fit(train_data)
    val predictions = model.transform(test_data)

    model.itemFactors.show(10, truncate = false)
    model.userFactors.show(10, truncate = false)

    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    val rmse = evaluator.evaluate(predictions)
    println(s"Root Mean Squared Error = $rmse")
  }

}
