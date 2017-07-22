package gui.shape

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color


abstract class Shape extends HasColor {
  def area: Double
  def primeter: Double
  def draw(gc: GraphicsContext): Unit
}

object Shape extends App{
  //if object extends App, it equals that we auto create a main method for code not if any def
  //this is the common way to make app in scala 
  //def main(args: Array[String]): Unit = {
    val c = Color.Black
    
    val rect = new Rectangle(2, 4, c)
    printShape(rect)
       
    val circle = new Circle(1, c)
    printShape(circle)
    
    val square = new Sqaure(1, c)
    printShape(square)
  //}
  
  def printShape(shape: Shape): Unit = {
    println(s"Area = ${shape.area} and Primeter = ${shape.primeter}")
  }
}
