/**
  * Generalised algebraic data types
  * http://www.scala-lang.org/old/node/48.html
  */

/** This examples illustrates the use of Guarded
  * Algebraic Data Types in Scala. For more information
  * please refer to the Scala specification available
  * from http://scala-lang.org/docu/.
  */
object gadts extends App {

  /* The syntax tree of a toy language */
  abstract class Term[T]

  /* An integer literal */
  case class Lit(x: Int) extends Term[Int]

  /* Successor of a number */
  case class Succ(t: Term[Int]) extends Term[Int]

  /* Is 't' equal to zero? */
  case class IsZero(t: Term[Int]) extends Term[Boolean]

  /* An 'if' expression. */
  case class If[T](c: Term[Boolean],
                   t1: Term[T],
                   t2: Term[T]) extends Term[T]

  /** A type-safe eval function. The right-hand sides can
    * make use of the fact that 'T' is a more precise type,
    * constraint by the pattern type.
    */
  def eval[T](t: Term[T]): T = t match {
    case Lit(n) => n

    // the right hand side makes use of the fact
    // that T = Int and so it can use '+'
    case Succ(u) => eval(u) + 1
    case IsZero(u) => eval(u) == 0
    case If(c, u1, u2) => eval(if (eval(c)) u1 else u2)
  }

  println(
    eval(If(IsZero(Lit(1)), Lit(41), Succ(Lit(41)))))
}