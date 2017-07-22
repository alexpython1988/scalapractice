package multithreadiing

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import akka.actor.TypedActor.PreStart
import akka.actor.TypedActor.PostStop

object HierarchyExample extends App {
  case object CreateChild
  case class SignalChild(order:Int)
  case class PrintMsg(order:Int)
  case class DividNumbers(n:Int, d: Int)
  case object CallError
  
  class ParentActor extends Actor{
    private var num = 0
    //private val children = collection.mutable.Buffer[ActorRef]()
    def receive = {
//      case s:String => println("String " + s)
//      case i:Int => println("Integer " + i)
      case CreateChild =>
        num += 1
        context.actorOf(Props[ChildActor], "Child" + num)
      case SignalChild(n) =>
        context.children.foreach { x => x ! PrintMsg(n) }
    }
    
    override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false){
      case ae:ArithmeticException => Resume
      case _:Exception => Restart
    }
  }
  
  class ChildActor extends Actor{
    def receive = {
      case PrintMsg(n) => println("index: " + n +" " + self)
      case DividNumbers(n, d) => println("result: " + n/d)
      case CallError => throw new RuntimeException("for test!")
    }
    
    //use these to process the resources -> close sockets and streams
    override def preStart() = {
      super.preStart()
      println("prestart")
    }
    
    override def postStop() = {
      super.postStop()
      println("postStop")  
    }
    
    override def preRestart(reason:Throwable, msg:Option[Any]) = {
      super.preRestart(reason, msg)   
      println("prerestart")
    }
    
     override def postRestart(reason:Throwable) = {
      super.postRestart(reason)   
      println("postrestart")
    }
  }
  
  val system = ActorSystem("HierarchryActor")
  val actor = system.actorOf(Props[ParentActor], "Parent1")
  val actor1 = system.actorOf(Props[ParentActor], "Parent2")
  
//  actor ! CreateChild
//  actor ! SignalChild(1)
//  actor ! CreateChild
//  actor ! CreateChild
//  actor ! SignalChild(2)
//  
//  actor1 ! CreateChild
//  val child1 = system.actorSelection("akka://HierarchryActor/user/Parent2/Child1")
//  child1 ! PrintMsg(3)

  actor ! CreateChild
//  actor ! CreateChild
  val child2 = system.actorSelection("akka://HierarchryActor/user/Parent1/Child1")
  child2 ! DividNumbers(9, 0)
  child2 ! DividNumbers(9, 3)
  child2 ! CallError
  
  Thread.sleep(100)
  system.terminate()
}




























