package multithreadiing

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object ActorSchedularExample extends App {
  case object Count 
 
  class SchedulorActor extends Actor{
    var n = 0
    def receive = {
      case Count => 
        n += 1
        println(n)
    }
  }
  
  
  val system = ActorSystem("Schedulor")
  val actor = system.actorOf(Props[SchedulorActor], "schedular1")
  //implicit val ec = system.dispatcher
  
  actor ! Count
  
  system.scheduler.scheduleOnce(1.second)(actor ! Count)
  
  val can = system.scheduler.schedule(0.second, 100.millis, actor, Count)
  
  Thread.sleep(1200)
  //avoid thread dead letter (main thread end before other threads)
  can.cancel()
  system.terminate()
}