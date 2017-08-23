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
import java.io.ObjectOutputStream
import java.io.ObjectInputStream

class DrawRectangle(
    private var x: Double,
    private var y: Double,
    private var w: Double,
    private var h: Double,
    @transient private var color: Color,
    val drawing: Drawing
    ) extends Drawable{
  override def toString: String = "Rect"
  
  def draw(gc: GraphicsContext): Unit = {
    gc.fill = color
    gc.fillRect(x, y, w, h)
  }
  
  @transient private var propPane: Node = null
  
  def toXML: xml.Node = {
    <drawable type="Rectangle" x={x.toString} y={y.toString()} w={w.toString()} h={h.toString()}>
      {Drawable.colorToXML(color)}
		</drawable>
  }
  
  override def propertiesPane: Node = {
     if(propPane == null){
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
      propPane = panel
    }
     return propPane
  }
  
  //customer serialization
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

object DrawRectangle{
  def apply(dd: Drawing, node: xml.Node): DrawRectangle = {
    val x = (node \ "@x").text.toDouble
    val y = (node \ "@y").text.toDouble
    val w = (node \ "@w").text.toDouble
    val h = (node \ "@h").text.toDouble
    val c = Drawable.xmlToColor((node \ "color").head)
    new DrawRectangle(x, y, w, h, c, dd)
  }
}



