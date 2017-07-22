package stackandqueue

import scala.reflect.ClassTag

class MyQueue[E: ClassTag] extends Queue1[E]{
  private var back = 0
  private var front = 0
  private var data = new Array[E](10)
  
  def enQueue(e: E): Unit = {
    if((back + 1) % data.length == front){
      val tmp = new Array[E](data.length * 2)
      for(i <- 0 until data.length){
        tmp(i) = data((front + i) % data.length)
      }
      
      back = data.length - 1
      front = 0
      data = tmp
    }
    
    data(back) = e
    back = (back + 1) % data.length
  }
  
  def deQueue(): E = {
    val ret = data(front)
    front = (front + 1) % data.length
    ret
  }
  
  def peek: E = data(front)
  
  def isEmpty: Boolean = front == back
}