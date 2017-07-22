package multithreadiing

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.concurrent.Await
import scala.util.Success
import scala.util.Failure
import scala.concurrent.duration._

object ParallelCollect extends App {
  //use par
  def fib(n :Int): Int = {
    if(n < 2) 1 else fib(n-1) + fib(n-2)
  }
//  
//  for(i <- (30 to 15 by -1).par){
//    println(fib(i))
//  }
  
//  var i = 0
//  for(j <- (1 to 100000000).par){
//    i += 1
//  }
//  println(i)
  
  //use future
//  println(1)
//  val f = Future{
//    println("test future")
//  }
//  Thread.sleep(1)
//  println(2)
  
  val f1 = Future{
    for(i <- 35 to 1 by -1) yield fib(i)
  }
  
//  f1.onComplete {
//    case Success(n) => println(n)
//    case Failure(ex) => println(ex)
//  }
  
  println(Await.result(f1, 1.seconds))
  
  Thread.sleep(3000)
}