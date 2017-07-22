package gui.drawing

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.layout.VBox
import scalafx.scene.control.ComboBox
import scalafx.event.ActionEvent
import scalafx.Includes._
import scala.collection.mutable


class DrawTransform (val drawing: Drawing) extends Drawable{
  private var _children = mutable.Buffer[Drawable]()
  private var propPane: Option[Node] = None
  private var transformType = DrawTransform.Translate
  private var v1 = 0.0
  private var v2 = 0.0
  
  def children = _children.map(i => i) 
  
  def addChild(d: Drawable): Unit = {
    _children += d
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
    if(propPane.isEmpty){
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
      propPane = Some(panel)
    }
    
    return propPane.get
  }
}

object DrawTransform extends Enumeration{
  val Rotate, Scale, Shear, Translate = Value
}