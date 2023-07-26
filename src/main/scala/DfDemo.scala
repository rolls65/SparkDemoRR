package org.itc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession, types}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object DfDemo extends App{
  System.setProperty("hadoop.home.dir", "C:\\winutils")
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkconf = new SparkConf()
  val spark = SparkSession.builder().config(sparkconf).getOrCreate()
  //way 1 DDL :
  val orderDDL= "orderId Int,orderDate String,customerId Int,status String"

  //way 2 programatic
  val orderschema = StructType(
    List(
      StructField("orderid",IntegerType,true),
      StructField("orderDate",StringType,true),
      StructField("customerId",IntegerType,true),
      StructField("status",StringType,true)
    )
  )
  //directly read from file
  //val ordersdf =spark.read.option("header",true).
    //option("inferSchema",true).csv(path = "C:\\Users\\rolls\\Downloads\\orders.csv")
  val ordersdf = spark.read.option("header", true).schema(orderschema).
    option("inferSchema", true).csv(path = "C:\\Users\\rolls\\Downloads\\orders.csv")

 // ordersdf.show(numRows = 5)

  val df =ordersdf.where(conditionExpr = "customerId > 10000").select(col = "orderid",cols="status")

  //df.show(numRows = 6)

  val df1 = ordersdf.where(conditionExpr = "customerId > 10000").
    groupBy(col1 = "status").count()
  //df1.repartition(numPartitions = 1).write.csv(path = "C:\\Users\\rolls\\Downloads\\output\\orderout")
  //df1.show(numRows = 5)

  df1.repartition(numPartitions = 1).write.format(source = "csv").mode(SaveMode.Overwrite).
    option("path","C:\\Users\\rolls\\Downloads\\output\\output2").save()

}
