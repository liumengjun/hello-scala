/**
  * Actors (see also Actors for Scala)
  * http://www.scala-lang.org/old/node/50.html
  */

import scala.actors._
import scala.actors.Actor._
import scala.actors.scheduler._

object Message {
  def main(args: Array[String]) {
//    val n = try {
//      Integer.parseInt(args(0))
//    }
//    catch {
//      case _: Throwable =>
//        println("Usage: examples.actors.Message <n>")
//        // Predef.exit
//        sys.exit(-1)
//    }
    val n = 3
    val nActors = 500
    val finalSum = n * nActors
    Scheduler.impl = new SingleThreadedScheduler

    def beh(next: Actor, sum: Int) {
      react {
        case value: Int =>
          val j = value + 1;
          val nsum = sum + j
          if (next == null && nsum >= finalSum) {
            println(nsum)
            System.exit(0)
          }
          else {
            if (next != null) next ! j
            beh(next, nsum)
          }
      }
    }

    def actorChain(i: Int, a: Actor): Actor =
      if (i > 0) actorChain(i - 1, actor(beh(a, 0))) else a

    val firstActor = actorChain(nActors, null)
    var i = n;
    while (i > 0) {
      firstActor ! 0;
      i -= 1
    }
  }
}