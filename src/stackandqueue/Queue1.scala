package stackandqueue

trait Queue1[E] {
  //FIFO
  def enQueue(e: E): Unit
  
  def deQueue(): E
  
  def peek: E
  
  def isEmpty: Boolean
}