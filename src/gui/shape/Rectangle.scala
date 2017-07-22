package gui.shape

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color


//final class cannot be extended
class Rectangle (val width: Double, val height: Double, val color: Color) extends Shape with HasColor{
  override def area: Double = {
    return width * height
  }
  
  override def primeter: Double = {
    return 2 * (width + height)
  }
  
  override def draw(gc: GraphicsContext): Unit = {
    //super.draw(gc)
    super.setColor(gc)
    gc.fillRect(0, 0, width, height)
  }
 
}