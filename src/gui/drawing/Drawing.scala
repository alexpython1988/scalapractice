package gui.drawing

import scalafx.scene.control.TreeItem
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Drawing {
  val root = new DrawTransform(this)
  private var _gc = None: Option[GraphicsContext]
  private var _vars = Map[String, Double]()
  
  def gc = _gc
  def gc_=(g: GraphicsContext): Unit = _gc = Some(g)
  def vars = _vars
  
  def setVar(varName: String, value: Double): Unit = _vars = _vars + (varName -> value)
   
  def draw(): Unit = {
    _gc.foreach { g =>
      g.fill = Color.White
      g.fillRect(0, 0, 1000, 1000)
      root.draw(g) }
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
}