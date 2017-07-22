package basic.oop.naming

//mutable version -> not clean way to do -> use immutable for general!
class Mutable2D (
    //pacakage private (private in naming), you can define the scope of private
    private[naming] var _x: Double, 
    //private[this] only private to current instance of the class, other instance cannot alter it
    private[this] var _y: Double)
{
  //get and set
  def x = _x
  
  def y = _y
  
  def setX(x: Double): Unit = {
    _x = x
  }
  
  def setY(y: Double): Unit = _y = y
  
  def plus(v: Mutable2D): Mutable2D = {
    _x += v.x
    _y += v.y
    return this
  }
  
  def minus(v: Mutable2D): Mutable2D  = {
    _x -= v.x
    _y -= v.y
    return this
  }
  
  def scale(sc: Double): Mutable2D = {
    _x *= sc
    _y *= sc
    return this
  }
  
  def magnitude: Double = math.sqrt(x * x + y * y)
}

//companion object -> can see private member of the class -> can store some data that not need for class but need for project
// we can define apply method in object instead of inside of class
object Mutable2D {
  def main(args: Array[String]): Unit = {
    var v1: Mutable2D = new Mutable2D(1,2)
    v1.setY(4)
    val v2: Mutable2D = new Mutable2D(2,2)
    v1.plus(v2)
    println(v1.magnitude)
  }
}


