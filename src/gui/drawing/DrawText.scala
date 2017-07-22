package gui.drawing

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.control.ColorPicker
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.layout.VBox

class DrawText(
    private var x: Double,
    private var y: Double,
    private var text: String,
    private var color: Color,
    val drawing: Drawing
    ) extends Drawable{
  
   def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillText(text, x, y)
  }
  
  override def toString: String = "Text"
  
  private var propPane: Option[Node] = None
  
  override def propertiesPane: Node = {
    if(propPane.isEmpty){
      val panel = new VBox
      val xFiled = DrawingMain.labeledTextField("x", x.toString(), s => {
        x = s.toDouble
        drawing.draw()
      })
      
      val yField = DrawingMain.labeledTextField("y", y.toString(), s => {
        y = s.toDouble
        drawing.draw()
      })
      
      val textField = DrawingMain.labeledTextField("test", text, s =>{
        text = s
        drawing.draw()
      })
      
      val colorPicker = new ColorPicker(color)
      colorPicker.onAction = (ae: ActionEvent) => {
        color = colorPicker.value()
        drawing.draw()
      }
      
      panel.children = List(xFiled, yField, textField, colorPicker)
      propPane = Some(panel)
    }
    
    return propPane.get
  }
}