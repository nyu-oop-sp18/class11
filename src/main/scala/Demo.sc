import scala.util.{Try, Success, Failure}
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

val f = Future {
  42
}

val f1 =
  for {
    r <- f
  } yield r + 1

Await.result(f1, Duration.Inf)