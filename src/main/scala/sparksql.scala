package org.itc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object sparksql extends App{
  System.setProperty("hadoop.home.dir", "C:\\winutils")
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkconf = new SparkConf()
  sparkconf.set("spark.app.name", "DataFrame Demo")
  sparkconf.set("spark.master", "local[*]")
  val spark = SparkSession.builder().config(sparkconf).getOrCreate()
  val ordersdf =spark.read.option("header",true).
    option("inferSchema",true).csv(path = "C:\\Users\\rolls\\Downloads\\orders.csv")
  ordersdf.createOrReplaceTempView(viewName = "order")
  spark.sql(sqlText = "select * from order  where order_id <3").show()
  import spark.implicits._
  val empDF = Seq(("Rupali", "Mumbai"), ("Mohsin", "pune"), ("Devi", "bangalore"), ("Alex", "hyderabad"))
    .toDF("fName", "city")

  val citiesDF = Seq(("Mumbai", "India"), ("pune", "India"), ("bangalore", "India"), ("hyderabad", "India"))
    .toDF("city", "country")
empDF.join(citiesDF,empDF.col(colName = "city") === citiesDF.col(colName = "city"))
}
