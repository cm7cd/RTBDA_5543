/**
  * Created by meetsriharsha on 9/6/2016.
  */

import org.apache.spark.{SparkContext, SparkConf}

object SparkTransformationAction {

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("SparkTransformationsActions").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val input = sc.textFile("BabyNames")
    //Applying filter transformation
    val babyNY = input.filter(line => line.contains("New York"))
    //Applying map transformation
    val babyNYRdd = babyNY.map(line => line.split(","))
    //Applying reduceByKey transformation
    val babyNYRddCount = babyNYRdd.map(n => (n(1), n(4).toInt)).reduceByKey(_+_)
    //Applying sortByKey transformation
    val babyNYRddCntSorted = babyNYRddCount.sortByKey()
    //Applying the action - saveAsTextFile()
    babyNYRddCntSorted.saveAsTextFile("BabyCount")
    //Applying the action - count()
    println("Number of records are: " + babyNYRddCntSorted.count())
    //Applying the actions - collect() & foreach
    babyNYRddCntSorted.collect().foreach{case(name,cnt) => println(name + ", " + cnt)}

  }
}
