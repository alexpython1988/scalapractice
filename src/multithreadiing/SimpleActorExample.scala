package multithreadiing

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object SimpleActorExample extends App{
  class SimpleActor extends Actor{
    def receive = {
      case s:String => println("String " + s)
      case i:Int => println("Number " + i)
    }
    
    def foo = println("foo")
  }
  
  val system = ActorSystem("SimpleAcotrSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor")
  
  println("main1")
  actor ! "Hello Akka"
  println("main2")
  actor ! 12
  println("main3")
  actor ! 'a'
  println("main4")
  
  system.terminate()
}