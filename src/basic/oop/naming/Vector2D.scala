package basic.oop.naming

//immutable Vector2D
class Vector2D (
    val x: Double, 
    val y: Double) 
{
//  def plus(v: Vector2D) = new Vector2D(x + v.x, y + v.y)
//  def minus(v: Vector2D) = new Vector2D(x - v.x, y - v.y)
//  def scale(c: Double) = new Vector2D(x * c, y * c)
  
  //use new naming 
//  def +(v: Vector2D) = new Vector2D(x + v.x, y + v.y)
//  def -(v: Vector2D) = new Vector2D(x - v.x, y - v.y)
//  def *(c: Double) = new Vector2D(x * c, y * c)
//  def /(c: Double) = new Vector2D(x / c, y / c)
  //use apply in object, invoking new by apply in object
  def +(v: Vector2D) = Vector2D(x + v.x, y + v.y)
  def -(v: Vector2D) = Vector2D(x - v.x, y - v.y)
  def *(c: Double) = Vector2D(x * c, y * c)
  def /(c: Double) = Vector2D(x / c, y / c)
  
  //apply method
  def apply(index: Int): Double = {
    index match {
      case 0 => return x
      case 1 => return y
      case _ => throw new IndexOutOfBoundsException("only 0 or 1 is valid.")
    }
  }
  
  //unary_ - or ! or ~ can be define and used as v = -v1; v = !v; v = ~v 
  def unary_-() = new Vector2D(-x, -y)
  
  def magnitude = math.sqrt(x * x + y * y)
  
  //operator allow for being used for naming + - * / | & ^ ! < > = ? $ \ : ~
  
  def +(x: Double, y: Double) = new Vector2D(this.x + x, this.y + y)
  
  override def toString(): String = {
    return s"x: $x; y: $y"
  }
}

//companion object -> can see private member of the class -> can store some data that not need for class but need for project
// we can define apply method in object instead of inside of class
object Vector2D {
  def main(args: Array[String]): Unit = {
//    val v1 = new Vector2D(1, 2)
//    val v2 = new Vector2D(2, 2)
      val v1 = Vector2D(1, 2)
      val v2 = Vector2D(2, 2)
//    val v3 = v1.plus(v2)
    //use new naming method
    val v3 = v1 + v2
    val v5 = v2 - v1
    
    val v4 = v1 + (1, 2) //v4 = v1.+(1,2) this can be simplified to what we wrote in the code
    
    val v6 = -v4
    
    println(v3.magnitude)
    println(v4.magnitude)
    println(v5.magnitude)
    println(v6.toString)
    //println(1 :: 2 :: 3 :: Nil) // -> Nil.::(3).::(2).::(1) -> :: is right associative
  }
  
  def apply(x: Double, y: Double) = new Vector2D(x, y)
}