package multithreadiing

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

//two thread communication
object ActorCountDown extends App {
  case class StartCount(n:Int, other:ActorRef)
  case class CountDown(n:Int)
  class CountDownActor extends Actor{
    def receive = {
      case StartCount(n, other) => 
        println(n)
        other ! CountDown(n-1)
      case CountDown(n) => 
        println(self)
        if(n > 0){
           println(n)
           sender() ! CountDown(n - 1)
        }else{
          context.system.terminate()
        }
    }
    
    def foo = println("foo")
  }
  
  val system = ActorSystem("ActorCountDown")
  val actor1 = system.actorOf(Props[CountDownActor], "CountDownActor1")
  val actor2 = system.actorOf(Props[CountDownActor], "CountDownActor2")
  
  actor1 ! StartCount(10, actor2)
}