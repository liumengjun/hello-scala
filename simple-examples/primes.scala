/**
  * A simple, although inefficient, way to calculate prime numbers
  * http://www.scala-lang.org/old/node/230.html
  */

/** Print prime numbers less than 100, very inefficiently */
object primes extends App {
  def isPrime(n: Int) = (2 until math.sqrt(n).ceil.toInt) forall (n % _ != 0)

  for (i <- 1 to 100 if isPrime(i)) println(i)
}