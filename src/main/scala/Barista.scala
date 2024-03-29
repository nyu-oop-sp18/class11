import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random
import scala.util.{Success, Failure}

object Barista extends App {
  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String
  case class Water(temperature: Int)

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("Start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") { 
      println("Are you joking?")
      throw new IllegalArgumentException(beans)
    }
    println("Finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("Heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("Hot, it's hot!")
    water.copy(temperature = 85)
  }
  
  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("Caution, steam!")
    Thread.sleep(Random.nextInt(2000))
    println("Shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("Happy brewing")
    Thread.sleep(Random.nextInt(2000))
    println("It's brewed!")
    "espresso"
  }

  
  def combine(espresso: Espresso, foam: FrothedMilk): Cappuccino = {
    println("Voila!")
    "cappuccino"
  }
  
  def prepareCappuccino(): Future[Cappuccino] = {
    val groundCoffee = grind("baked beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      espresso <- brew(ground, water)
      foam <- frothedMilk
    } yield combine(espresso, foam)
  }
  
  val future = prepareCappuccino() map {
    case "cappuccino" => "Yum!"
    case drink => s"I didn't order $drink!"
  } recover {
    case ex => "I want my money back!"
  } 
  
  val result = Await.result(future, Duration.Inf)
  
  println(result)
}

