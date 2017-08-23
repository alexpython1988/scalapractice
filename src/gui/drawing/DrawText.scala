package gui.drawing

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scalafx.scene.control.ColorPicker
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.layout.VBox
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DrawText(
    private var x: Double,
    private var y: Double,
    private var text: String,
    @transient private var color: Color,
    val drawing: Drawing
    ) extends Drawable{
  
   def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillText(text, x, y)
  }
  
   def toXML: xml.Node = {
    <drawable type="Text" x={x.toString} y={y.toString()}>
			<text>{text}</text>
			{Drawable.colorToXML(color)}
		</drawable>
  }
   
  override def toString: String = "Text"
  
  @transient private var propPane: Node = null
  
  override def propertiesPane: Node = {
    if(propPane == null){
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
      propPane = panel
    }
    
    return propPane
  }
  
    private def writeObject(oos: ObjectOutputStream): Unit = {
    oos.defaultWriteObject()
    oos.writeDouble(color.red)
    oos.writeDouble(color.green)
    oos.writeDouble(color.blue)
    oos.writeDouble(color.opacity)
  }
  
  private def readObject(ois: ObjectInputStream): Unit = {
    ois.defaultReadObject()
    color = Color(ois.readDouble(), ois.readDouble(), ois.readDouble(), ois.readDouble())
  }
}

object DrawText{
  def apply(d: Drawing, node: xml.Node): DrawText = {
    val x = (node \ "@x").text.toDouble
    val y = (node \ "@y").text.toDouble
    val c = Drawable.xmlToColor((node \ "color").head)
    val t = (node \ "text").text
    new DrawText(x, y, t, c, d)
  }
}