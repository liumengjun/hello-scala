/**
  * Maps are easy to use in Scala
  * http://www.scala-lang.org/old/node/228.html
  */

/** Maps are easy to use in Scala. */
object Maps {
  val colors = Map(
    "red" -> 0xFF0000,
    "turquoise" -> 0x00FFFF,
    "black" -> 0x000000,
    "orange" -> 0xFF8040,
    "brown" -> 0x804000
  )

  def main(args: Array[String]) {
    for (name <- args) println(
      colors.get(name) match {
        case Some(code) =>
          name + " has code: " + "0x%06x".format(code)
        case None =>
          "Unknown color: " + name
      }
    )
  }
}