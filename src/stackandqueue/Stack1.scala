package stackandqueue

trait Stack1[E] {
  //FILO
  def push(e: E): Unit
  
  def pop(): E
  
  def peek: E
  
  def isEmpty: Boolean
  
}