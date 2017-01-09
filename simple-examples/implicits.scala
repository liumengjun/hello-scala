/**
  * Define a new method 'sort' on arrays without changing their definition
  * http://www.scala-lang.org/old/node/227.html
  */

/* Defines a new method 'sort' for array objects */
object implicits extends App {
  implicit def arrayWrapper[A: ClassManifest](x: Array[A]) =
    new {
      def sort(p: (A, A) => Boolean) = {
        util.Sorting.stableSort(x, p);
        x
      }
    }

  val x = Array(2, 3, 1, 4)

  val x_odds = x.filter((i) => (i & 0x1) == 1)
  println(x_odds.toList)

  val x_cubed = x.map((i) => i * i * i)
  println(x_cubed.toList)

  x.sort((x: Int, y: Int) => x < y)
  //  println("x = " + x)
  //  for (i <- x) println(i)
  println("x = " + x.toList)
}