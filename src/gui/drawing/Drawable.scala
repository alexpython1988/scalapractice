package gui.drawing

import scalafx.scene.Node
import scalafx.scene.canvas.GraphicsContext

trait Drawable {
  val drawing: Drawing
  def propertiesPane: Node
  def draw(gc: GraphicsContext): Unit
}