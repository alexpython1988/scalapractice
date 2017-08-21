package multithreadiing

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.atomic.AtomicInteger

object ThreadSum extends App {
  val nums = Array.fill(10000000)(math.random)
  var sum = 0.0
  val numThreads = 12
  val sums = Array.fill(numThreads)(0.0)
  
  val threads = for(i <- 0 until numThreads) yield new Thread{
    override def run: Unit = {
      for(j <- i*nums.length/numThreads until (i+1)*nums.length/numThreads){
        sums(i) += nums(j)
      }
    }
  }
  
  threads.foreach(x => x.start())
  
  threads.foreach { x => x.join() }
  
  println(sums.sum + " = " + nums.sum)
  
  //use future to replace above code
  
  val nums1 = Array.fill(10000000)(math.random)
  var sum1 = 0.0
  val numThreads1 = 12
  val sums1 = for(i <- 0 until numThreads) yield Future{
    var sum = 0.0
    for(j <- i*nums1.length/numThreads until (i+1)*nums1.length/numThreads){
      sum += nums1(j)
    }
    
    sum
  }
  
  println(1)
  Future.sequence(sums1).foreach(s => println(s.sum + " = " + nums1.sum))
  Thread.sleep(1000)
  //Thread.`yield`
  
  {
    var cnt = 0
    var start = System.nanoTime()
    for (i <- 1 to 10000000) cnt+= 1
    println(cnt +  " " + (System.nanoTime() - start))
  }
  
  {
    var cnt = new AtomicInteger(0)
    var start = System.nanoTime()
    for (i <- (1 to 10000000).par) cnt.incrementAndGet()
    println(cnt +  " " + (System.nanoTime() - start))
  }
}








