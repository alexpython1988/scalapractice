package polymorphism

import scala.io.StdIn
import java.util.function.UnaryOperator

object SampleCode {
  def square(x: Int) = x * x
  
  def sumValue(x: Int): Double = {
    if(x < 1)
      return 0.0
    else
      StdIn.readDouble() + sumValue(x - 1)
  }
  
  def productValue(x: Int): Double = {
    if(x < 1)
      return 1.0
    else
      StdIn.readDouble() * sumValue(x - 1)
  }
  
  //abstraction of sumValue and productValue
  def combineValue(x: Int, base: Double, op: (Double, Double) => Double): Double = {
    if(x < 0)
      return base //abstract the base case return value
    else
      return op(StdIn.readDouble(), combineValue(x - 1, base, op)) //abstract +, *
  }
  
  def +(a: Double, b: Double): Double = {
    return a + b
  }
  
   def *(a: Double, b: Double): Double = {
    return a * b
  }
  
  def main(args: Array[String]): Unit = {
    val res = combineValue(3, 0.0, SampleCode.+)
    println(res)
    
    val res1 = combineValue(3, 1.0, SampleCode.*)
    println(res1)
  }
}