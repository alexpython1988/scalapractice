package gui.drawing

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.layout.VBox
import scalafx.scene.control.ComboBox
import scalafx.event.ActionEvent
import scalafx.Includes._
import scala.collection.mutable

class DrawTransform (val drawing: Drawing,
  private var _children: mutable.Buffer[Drawable] = mutable.Buffer[Drawable](),
  private var transformType: DrawTransform.Value = DrawTransform.Translate,
  private var v1: Double = 0.0,
  private var v2: Double = 0.0) extends Drawable{
  @transient private var propPane: Node = null
  
  def children = _children.map(i => i) 
  
  def addChild(d: Drawable): Unit = {
    _children += d
  }
  
  def toXML: xml.Node = {
    <drawable type="Transform" transType={transformType.toString} v1={v1.toString()} v2={v2.toString()}>
      {_children.map(_.toXML)}
		</drawable>
  }
  
  
  def draw(gc: GraphicsContext): Unit = {
    gc.save()
    
    transformType match {
      case DrawTransform.Rotate => gc.rotate(v1)
      case DrawTransform.Shear => gc.transform(1.0, v1, v2, 1.0, 0.0, 0.0)
      case DrawTransform.Scale => gc.scale(v1, v2)
      case DrawTransform.Translate => gc.translate(v1, v2)
    }
    
    _children.foreach { x => x.draw(gc) }
    
    gc.restore()
  }
  
  override def toString: String = "Transform"
  
  override def propertiesPane: Node = {
    if(propPane == null){
      val panel = new VBox
      val combo = new ComboBox(DrawTransform.values.toSeq)
      
      combo.onAction = (ae:ActionEvent) => {
        transformType = combo.selectionModel.value.selectedItem.value
        drawing.draw()
      }
      
      combo.selectionModel.value.select(transformType)
      val v1Field = DrawingMain.labeledTextField("x/theta", v1.toString(), s => {
        v1 = s.toDouble
        drawing.draw()
      })
      
      val v2Field = DrawingMain.labeledTextField("y", v2.toString(), s => {
        v2 = s.toDouble
        drawing.draw()
      })
      
      panel.children = List(combo, v1Field, v2Field)
      propPane = panel
    }
    
    return propPane
  }
}

object DrawTransform extends Enumeration{
  val Rotate, Scale, Shear, Translate = Value
  
  def apply(d: Drawing, n: xml.Node): DrawTransform = {
    val children = (n \ "drawable").map(x => Drawable(d, x)).toBuffer
    val typeString = (n \ "@transType").text
    val transType = DrawTransform.values.find(_.toString == typeString).get
    val v1 = (n \ "@v1").text.toDouble
    val v2 = (n \ "@v2").text.toDouble
    new DrawTransform(d, children, transType, v1, v2)
  }
}