import scala.concurrent.{ Future, Promise, Await }
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object ProducerConsumer extends App {
  
  val p = Promise[Int]
  val f = p.future

  def produceSomething(): Int = 42
  
  def continueDoingSomethingUnrelated(): Unit = ()
  
  val producer = Future {
    val r = produceSomething()
    p success r
    continueDoingSomethingUnrelated()
  }

  def startDoingSomething(): Unit = ()
  
  def doSomethingWithResult(r: Int): Unit = println(s"result: $r")
  
  val consumer = 
    for {
      _ <- Future(startDoingSomething())
      r <- f 
    } yield doSomethingWithResult(r)
  
  
  Await.result(consumer, Duration.Inf)
}

