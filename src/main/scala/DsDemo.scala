package org.itc

import DfDemo.sparkconf
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DsDemo extends App{
  System.setProperty("hadoop.home.dir", "C:\\winutils")
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkconf = new SparkConf()
  sparkconf.set("spark.app.name", "DataFrame Demo")
  sparkconf.set("spark.master", "local[*]")
  val spark = SparkSession.builder().config(sparkconf).getOrCreate()
  val ordersdf =spark.read.option("header",true).
  option("inferSchema",true).csv(path = "C:\\Users\\rolls\\Downloads\\orders.csv")

  case class orderdata(order_id:Int,order_date:String,order_customer_id:Int,order_status:String)
  import spark.implicits._
  val orderDS = ordersdf.as[orderdata]
  orderDS.filter(conditionExpr = "order_id <10").show(numRows = 5)

}
