/**
  * Java varargs can be easily used in Scala as well
  * http://www.scala-lang.org/old/node/232.html
  */

/** Using Java varargs */
object varargs extends App {
  val msg = java.text.MessageFormat.format(
    "At {1,time} on {1,date}, there was {2} on planet {0}.",
    "Hoth", new java.util.Date(), "a disturbance in the Force")
  println("Message=" + msg)
}