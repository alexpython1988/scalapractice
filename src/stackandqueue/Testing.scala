package stackandqueue

import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import scala.util.Random

class Testing {
  private var stack: MyStack[Int] = null
  private var queue: MyQueue[Int] = null
  
  @Before
  def makeStack(){
      stack = new MyStack[Int]()
      queue = new MyQueue[Int]()
  }
  
  @Test
  def emptyOnCreate(){
    assertTrue(stack.isEmpty)
  }
  
  @Test
  def nonemptyOnPush(){
    stack.push(1)
    assertFalse(stack.isEmpty)
  }
  
  @Test
  def pushAndPop1(){
    stack.push(1)
    assertTrue(stack.peek == 1)
    assertTrue(stack.pop == 1)
  }
  
  @Test
  def pushPopPushPop(){
    stack.push(1)
    assertTrue(stack.pop() == 1)
    
    stack.push(10)
    val i = stack.pop()
    assertTrue(i == 10)
    assertTrue(stack.isEmpty)
  }
  
  @Test
  def reverseTest(){
    val nums = Array.fill(100)(Random.nextInt())
    nums.foreach { x => stack.push(x); queue.enQueue(x) }
    nums.reverse.foreach { x => assertEquals(x, stack.peek); assertEquals(x, stack.pop) }
    nums.foreach { x => assertEquals(x, queue.peek); assertEquals(x, queue.deQueue()) } 
  }
  
  @Test
  def push100(){
    for(i <- 0 until 100){
      stack.push(i)
      queue.enQueue(i)
    }
    
    assertTrue(stack.peek == 99)
    assertTrue(queue.peek == 0)
  }
  
  @Test
  def RPNcalcTestTwo(){
    assertEquals(5, RPNCalc("2,3,+".split(","), null), 0.0)
    assertEquals(-1, RPNCalc("2,3,-".split(","), null), 0.0)
    assertEquals(6, RPNCalc("2,3,*".split(","), null), 0.0)
    assertEquals(1.5, RPNCalc("3,2,/".split(","), null), 0.0)
  }
  
  @Test
  def RPNcalcTestThree(){
    assertEquals(25, RPNCalc("2,3,+,5,*".split(","), null), 0.0)
    assertEquals(-0.5, RPNCalc("2,3,-,0.5,+".split(","), null), 0.0)
    assertEquals(1, RPNCalc("2,3,*,6,/".split(","), null), 0.0)
    assertEquals(6, RPNCalc("3,2,/,4,*".split(","), null), 0.0)
  }
  
  @Test
  def RPNCalcTestWithVars(){
    val vars = Map[String, Double]("x" -> 2, "y" -> 3)
    assertEquals(25, RPNCalc("x,y,+,5,*".split(","), vars), 0.0)
    assertEquals(-0.5, RPNCalc("x,y,-,0.5,+".split(","), vars), 0.0)
    assertEquals(1, RPNCalc("x,y,*,6,/".split(","), vars), 0.0)
    assertEquals(6, RPNCalc("y,x,/,4,*".split(","), vars), 0.0)
  }
}



















