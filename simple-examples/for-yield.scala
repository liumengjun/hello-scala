/**
  * An example of the for and yield constructs
  * http://www.scala-lang.org/old/node/225.html
  */

/** Turn command line arguments to uppercase */
object ForYieldDemo {
  def main(args: Array[String]) {
    val res = for (a <- args) yield a.toUpperCase
    println("Arguments: " + res.toString)
    for (a <- res) println(a)
  }
}