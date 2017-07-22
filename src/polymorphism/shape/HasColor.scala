package polymorphism.shape

import scalafx.scene.paint.Color
import scalafx.scene.canvas.GraphicsContext

//cannot pass val through constructor
trait HasColor {
  val color: Color
  
  def setColor(gc: GraphicsContext): Unit = {
    gc.fill = color
  }
}