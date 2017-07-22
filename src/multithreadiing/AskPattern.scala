package multithreadiing

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.pattern.ask
import akka.util.Timeout

object AskPattern extends App {
  case object AskName
  case class NameResponce(name:String)
  case class AskNameof(other: ActorRef)
  
  class AskActor(val name: String) extends Actor{
    def receive = {
      case AskName => sender ! NameResponce(name)
//      case i:Int => println("integer: " + i)
      case AskNameof(other) =>
        val f = other ? AskName
        f.onComplete{
          case Success(NameResponce(n)) =>
            println("other name is " + n)
          case Success(s) => 
            println("Not valid information")
          case Failure(ex) =>
            println("failed " + ex)
        }
    }
  }
  
  val system = ActorSystem("AskActor")
  val actor = system.actorOf(Props(new AskActor("pat")), "AskActor1")
  val actor2 = system.actorOf(Props(new AskActor("val")), "AskActor2")
  
  implicit val timeout = Timeout(1.seconds)
  val res = actor ? AskName //return future
  res.foreach { x => println("name is " + x) }
  
  actor ! AskNameof(actor2)
  
  system.terminate()
}