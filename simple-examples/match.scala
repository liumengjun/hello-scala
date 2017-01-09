/**
  * Using pattern matching to recognize command line arguments
  * http://www.scala-lang.org/old/node/229.html
  */

/** Basic command line parsing. */
object MatchDemo {
  var verbose = false

  def main(args: Array[String]) {
    for (a <- args) a match {
      case "-h" | "-help" =>
        println("Usage: scala Main [-help|-verbose]")
      case "-v" | "-verbose" =>
        verbose = true
      case x =>
        println("Unknown option: '" + x + "'")
    }
    if (verbose)
      println("How are you today?")
  }
}