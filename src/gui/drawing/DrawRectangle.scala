package gui.drawing

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TextField
import scalafx.scene.layout.VBox
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.control.ColorPicker

class DrawRectangle(
    private var x: Double,
    private var y: Double,
    private var w: Double,
    private var h: Double,
    private var color: Color,
    val drawing: Drawing
    ) extends Drawable{
  override def toString: String = "Rect"
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillRect(x, y, w, h)
  }
  
  private var propPane: Option[Node] = None
  
  override def propertiesPane: Node = {
     if(propPane.isEmpty){
      val panel = new VBox
      
      val xField = DrawingMain.labeledTextField("x", x.toString(), s => {
        x = s.toDouble
        drawing.draw()
      })
      
      val yField = DrawingMain.labeledTextField("y", y.toString(), s => {
        y = s.toDouble
        drawing.draw()
      })
      
      val wField = DrawingMain.labeledTextField("w", w.toString(), s => {
        w = s.toDouble
        drawing.draw()
      })
      
      val hField = DrawingMain.labeledTextField("h", h.toString(), s => {
        h = s.toDouble
        drawing.draw()
      })
      
      val colorPicker = new ColorPicker(color)
      colorPicker.onAction = (ae: ActionEvent) => {
        color = colorPicker.value()
        drawing.draw()
      }
      
      panel.children = List(xField, yField, wField, hField, colorPicker)
      propPane = Some(panel)
    }
     return propPane.get
  }
}