package gui.shape

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color


class Circle (val radius: Double, val color: Color) extends Shape {
   override def area: Double = {
    return math.Pi * radius * radius
  }
  
  override def primeter: Double = {
    return 2 * math.Pi * radius
  }
  
  override def draw(gc: GraphicsContext): Unit = {
    //super.draw(gc)
    super.setColor(gc)
    gc.fillOval(0, 0, radius, radius)
  }
}