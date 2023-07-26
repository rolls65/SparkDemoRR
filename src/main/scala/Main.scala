package org.itc
  import org.apache.log4j.{Level, Logger}
  import org.apache.spark.SparkContext

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    System.setProperty("hadoop.home.dir", "C:\\winutils")
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext(master = "local[1]", appName = "WordCountDemo")
    val rdd1 = sc.textFile(args(0))
    //input is a line, output as words, 1 to M
    val rdd2 = rdd1.flatMap(x => x.split(" "))
    //input is words - ouput word,1, generating key,value pair, 1 to 1
    val rdd3 = rdd2.map(x => (x, 1))
    val rdd_out = rdd1.flatMap(x => x.split(" ")).map(x => (x.toLowerCase(), 1)).reduceByKey((x, y) => x + y).sortBy(_._2)
    //rdd_out.collect.foreach(println)
    rdd3.collect.foreach(println)
  }
}