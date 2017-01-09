/**
  * Created by liumengjun on 1/9/17.
  */
// 线性递归fibonacci
def fibonacci(n: Int): Int = {
  if (n <= 0) 0
  else if (n == 1) 1
  else fibonacci(n - 2) + fibonacci(n - 1)
}
for (i <- 1 to 20) {
  print(fibonacci(i) + " ")
}
println
// 尾递归fibonacci
def fibonacci2(n: Int) = {
  def fibInner(n: Int, a: Int, b: Int): Int = {
    if (n <= 0) a
    else if (n == 1) b
    else fibInner(n - 2, a + b, b + a + b)
  }

  fibInner(n, 0, 1)
}
for (i <- 1 until 21) {
  print(fibonacci2(i) + " ")
}
println

// def methods
def square(x: Double) = x * x
def sumOfSquares(x: Double, y: Double) = square(x) + square(y)

def abs(x: Double) = if (x >= 0) x else -x

def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

// currying
def sum(f: Int => Int)(a: Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f)(a + 1, b)

def id(x: Int): Int = x
def square(x: Int): Int = x * x
def powerOfTwo(x: Int): Int = if (x == 0) 1 else 2 * powerOfTwo(x - 1)

val sumInts = sum(id) _ // make this conversion explicit by writing `sum _`
def sumSquares = sum(x => x * x)(_, _) // make this conversion explicit by writing `sum(_)(_,_)`
def sumPowersOfTwo = sum(powerOfTwo) _

//println(sum)
println(sumInts)

println(sumInts(1, 5))
println(sumSquares(1, 5))
println(sumPowersOfTwo(1, 5))

val tolerance = 0.000001
def isCloseEnough(x: Double, y: Double) = abs((x - y) / x) < tolerance
// nested methods
def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  def iterate(guess: Double): Double = {
    val next = f(guess)
    //    println(next)
    if (isCloseEnough(guess, next)) next else iterate(next)
  }

  iterate(firstGuess)
}
/*
Example: Square Roots by Newton’s Method
 */
//def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)
def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2
def sqrt(x: Double) = fixedPoint(averageDamp(y => x / y))(1.0)

var sqrtOf2 = sqrt(2.0)
println(sqrtOf2)


// trait and classes
trait Shape {
  var edges: Int

  def length: Double

  def circumference: Double

  def area: Double
}

trait Rectangle0 extends Shape {
  var edges = 4
  var width: Double
  var height: Double

  def length = Math.max(width, height)

  def circumference = width + width + height + height

  def area = width * height
}

class Rectangle(w: Double, h: Double) extends Rectangle0 {
  var width = w
  var height = h
}

class Square(length: Double) extends Rectangle0 {
  var width = length
  var height = length

  def setLength(len: Double): Unit = {
    this.width = len
    this.height = len
  }
}

//var rect = new Rectangle0()  // Traits are abstract classes

val rect0 = new Rectangle(3, 4)
println(rect0.toString())
println(rect0.edges)
println(rect0.length)
println(rect0.area)
println(rect0.circumference)

val rect2 = new Square(5)
println(rect2.toString())
println(rect2.edges)
println(rect2.length)
println(rect2.area)
println(rect2.circumference)

rect0.edges = 5;
rect2.edges = 3;
println(rect0.edges)
println(rect2.edges)

rect2.setLength(6)
println(rect2.length)
println(rect2.area)
println(rect2.circumference)


// case classes and pattern match
abstract class Expr {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
  }
}

case class Number(var n: Int) extends Expr

case class Sum(e1: Expr, e2: Expr) extends Expr

// Caseclassesimplicitlycomewithaconstructorfunction,withthesamename as the class
//def Number(n: Int) = new Number(n)
//def Sum(e1: Expr, e2: Expr) = new Sum(e1, e2)
println(Sum(Sum(Number(1), Number(2)), Number(3)))
println(Sum(Number(1), Number(2)) == Sum(Number(1), Number(2)))
var numberObj = Number(3)
println(numberObj)
numberObj.n = 4 // 默认是常量val，除非加上"var"
println(numberObj)
println(numberObj.n)

def eval(e: Expr): Int = e match {
  case Number(n) => n
  case Sum(l, r) => eval(l) + eval(r)
}
println(eval(Sum(Sum(Number(1), Number(2)), Number(3))))
println(Sum(Sum(Number(1), Number(2)), Number(3)).eval)

// 调用eval不需要加"()"。如果定义eval时有"()"，那么调用时"()"可加可不加


// generic types
abstract class Stack[A] {
  def push(x: A): Stack[A] = new NonEmptyStack[A](x, this)

  def isEmpty: Boolean

  def top: A

  def pop: Stack[A]
}

class EmptyStack[A] extends Stack[A] {
  def isEmpty = true

  def top = error("EmptyStack.top")

  def pop = error("EmptyStack.pop")
}

class NonEmptyStack[A](elem: A, rest: Stack[A]) extends Stack[A] {
  def isEmpty = false

  def top = elem

  def pop = rest
}

val x = new EmptyStack[Int]
val y = x.push(1).push(2)
println(y.pop.top)

def isPrefix[A](p: Stack[A], s: Stack[A]): Boolean = {
  p.isEmpty ||
    p.top == s.top && isPrefix[A](p.pop, s.pop)
}

val s1 = new EmptyStack[String].push("abc")
val s2 = new EmptyStack[String].push("abx").push(s1.top)
println(isPrefix[String](s1, s2))


// function is object
val f: (AnyRef => Int) = x => x.hashCode()
val g: (String => Int) = f
println(g("abc"))
val plus1: (Int => Int) = (x: Int) => x + 1
println(plus1(2))
println(plus1.getClass())

/*
This is expanded into the following object code.
val plus1: Function1[Int, Int] = new Function1[Int, Int] {
  def apply(x: Int): Int = x + 1
}
println(plus1.apply(2))
println(plus1.getClass())
*/
/*
Equivalently, but more verbosely, one could have used a local class:
val plus1: Function1[Int, Int] = {
  class Local extends Function1[Int, Int] {
    def apply(x: Int): Int = x + 1
  }
  new Local: Function1[Int, Int]
}
println(plus1.apply(2))
println(plus1.getClass())
*/


// list and for-loop
case class Book(title: String, authors: List[String])

val books: List[Book] = List(
  Book("Structure and Interpretation of Computer Programs",
    List("Abelson Harold", "Sussman Gerald J.")),
  Book("Principles of Compiler Design",
    List("Aho Alfred", "Ullman Jeffrey")),
  Book("Programming in Modula-2",
    List("Wirth Niklaus")),
  Book("Introduction to Functional Programming",
    List("Bird Richard")),
  Book("The Java Language Specification",
    List("Gosling James", "Joy Bill", "Steele Guy", "Bracha Gilad")),
  Book("The Essential of Scala",
    List("Gosling James", "Bird Richard", "Wirth Niklaus")))

var booksOfUllman = for (b <- books; a <- b.authors if a startsWith "Ullman") yield b.title
println(booksOfUllman)
var booksOnProgram = for (b <- books if (b.title indexOf "Program") >= 0) yield b.title
println(booksOnProgram)
var authorsWritingManyBooks = for (b1 <- books; b2 <- books if b1 != b2;
                                   a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
  yield a1
println(removeDuplicates(authorsWritingManyBooks))

def removeDuplicates[A](xs: List[A]): List[A] =
  if (xs.isEmpty) xs
  else xs.head :: removeDuplicates(xs.tail filter (x => x != xs.head))


println("List.range")
for (i <- List.range(1, 5))
  print(i + " ")
println
println("Stream.range")
for (i <- Stream.range(1, 5))
  print(i + " ")
println
println("Iterator.range")
for (i <- Iterator.range(1, 5))
  print(i + " ")
println
println("Range")
for (i <- Range(1, 5))
  print(i + " ")
println


// lazy val
case class Employee(id: Int, name: String,
                    managerId: Int) {
  //  val manager: Employee = Db.get(managerId)
  lazy val manager: Employee = Db.get(managerId)
  lazy val team: List[Employee] = Db.team(id)
}

object Db {
  val table = Map(1 -> (1, "Haruki Murakami", -1),
    2 -> (2, "Milan Kundera", 1),
    3 -> (3, "Jeffrey Eugenides", 1), 4 -> (4, "Mario Vargas Llosa", 1), 5 -> (5, "Julian Barnes", 2))

  def team(id: Int) = {
    for (rec <- table.values.toList; if rec._3 == id)
      yield recToEmployee(rec)
  }

  def get(id: Int) = recToEmployee(table(id))

  private def recToEmployee(rec: (Int, String, Int)) = {
    println("[db] fetching " + rec._1)
    Employee(rec._1, rec._2, rec._3)
  }
}

var emp2 = Db.get(2)
println(emp2)
println(emp2.manager)
