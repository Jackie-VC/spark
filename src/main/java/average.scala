import java.io.{File, PrintWriter}

import org.apache.spark.{SparkConf, SparkContext}


object average extends App {
  override def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("average").setMaster("local")
    val sc = new SparkContext(conf)

    var file = sc.textFile("access_log")
    var lines = file.map(line => line.split(" "))
      .filter(x => x.length == 10)
      .filter(x => x(9)
        .matches("\\d+"))
    var sum = lines.map(x => (x(0), Integer.parseInt(x(9)).toLong)).reduceByKey(_ + _)
    var cnt = lines.map(x => (x(0), 1.toLong)).reduceByKey(_ + _)
//    var avg = sum.join(cnt).map(x => (x.1, x.2. 1.toFloat / x.2))

    val writer = new PrintWriter(new File("output.txt"))
//    val res = avg.collect()
//    for (n <- res) writer.println(n.toString)

    writer.print(sum.join(cnt))
    writer.close()

  }
}
