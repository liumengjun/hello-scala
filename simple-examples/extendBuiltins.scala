/**
  * Adding "!" as a new method on integers
  * http://www.scala-lang.org/old/node/226.html
  */

/* Adding ! as a method on int's */
object extendBuiltins extends App {
  def fact(n: Int): BigInt =
    if (n == 0) 1 else fact(n-1) * n
  class Factorizer(n: Int) {
    def ! = fact(n)
  }
  implicit def int2fact(n: Int) = new Factorizer(n)

  println("10! = " + (10!))
}