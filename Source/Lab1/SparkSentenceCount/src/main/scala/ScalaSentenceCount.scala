/**
  * Created by meetsriharsha on 8/27/2016.
  */


import org.apache.spark.{SparkContext, SparkConf}


object SparkSentenceCount {

  def main(args: Array[String]) {


    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    val input=sc.textFile("input")

    val wc=input.flatMap(line=>{line.split("[\\.\\?] ")}).map(sentence=>(sentence,1)).cache()

    val output=wc.reduceByKey(_+_).sortBy(c=>c._1, true)


    output.saveAsTextFile("output")


  }

}

