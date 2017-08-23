package gui.drawing

import scalafx.scene.control.TreeItem
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Drawing() extends Serializable{
  private var root = new DrawTransform(this)
  private var _vars = Map[String, Double]()
  //private var _gc = None: Option[GraphicsContext]
  @transient private var _gc = null: GraphicsContext
  
  def gc = _gc
  def gc_=(g: GraphicsContext): Unit = _gc = g //Some(g)
  def vars = _vars
  
  def setVar(varName: String, value: Double): Unit = _vars = _vars + (varName -> value)
   
  def draw(): Unit = {
//    _gc.foreach { g =>
//      g.fill = Color.White
//      g.fillRect(0, 0, 1000, 1000)
//      root.draw(g) }
    if(_gc != null){
      gc.fill = Color.White
      gc.fillRect(0, 0, 1000, 1000)
      root.draw(gc)
    }
  }
  
  def makeTree(): TreeItem[Drawable] = {
    def helper(d: Drawable): TreeItem[Drawable] = d match {
      case dt: DrawTransform => 
        val item = new TreeItem(d)
        dt.children.foreach { x => item.children += helper(x) }
        return item
      case _ => return new TreeItem(d)
    }
    
    helper(root)
  }
  
  def toXML: xml.Node = {
    <drawing>
			{root.toXML}
			{_vars.map(t => <var key={t._1} value={t._2.toString()} />)}
		</drawing>
  }
}

object Drawing {
  def apply(n: xml.Node): Drawing = {
     val d = new Drawing
     d.root = DrawTransform(d, (n \ "drawable").head)
     d._vars = (n \ "vars").map{ v =>
       val key = (v \ "@key").text
       val value = (v \ "@value").text.toDouble
       key -> value
     }.toMap
     d
  }
}


