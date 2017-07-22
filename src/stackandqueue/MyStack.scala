package stackandqueue

import scala.reflect.ClassTag

class MyStack[E: ClassTag] extends Stack1[E]{
  private var data = new Array[E](10)
  private var top = 0
  
  def push(e: E): Unit = {
    //double capacity
    if(top >= data.length){
//      val temp = data
//      data = new Array[E](temp.length * 2)
//      for(i <- 0 to temp.length){
//        data(i) = temp(i)
//      }
      
      val tmp = new Array[E](data.length * 2)
      Array.copy(data, 0, tmp, 0, data.length)
      data = tmp
    }
    
    data(top) = e
    top += 1
  }
  
  def pop(): E = {
    top -= 1
    data(top)
  }
  
  def peek: E = {
    data(top - 1)
  }
  
  def isEmpty: Boolean = {
    top == 0
  }
}