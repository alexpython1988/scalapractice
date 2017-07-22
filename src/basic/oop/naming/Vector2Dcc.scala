package basic.oop.naming

case class Vector2Dcc (x: Double, y: Double) { //no need val for x and y
  //implement hashcode, equals, toString, apply methods as default
  def +(v: Vector2Dcc): Vector2Dcc = Vector2Dcc(x + v.x, y + v.y) //no need to implement apply by hand
  def -(v: Vector2D) = Vector2Dcc(x - v.x, y - v.y)
  def *(c: Double) = Vector2Dcc(x * c, y * c)
  def /(c: Double) = Vector2Dcc(x / c, y / c)
  def magnitude = math.sqrt(x * x + y * y)
}

object Vector2Dcc {
  def main(args: Array[String]): Unit = {
     val v1 = Vector2Dcc(1, 2)
     val v2 = Vector2Dcc(2, 2)
     val v3 = v1 + v2
     println(v3) //Vector2Dcc(3.0,4.0) -> defaut toString method
     val v4 = v3.copy(y = 10)
     println(v4)
  }
}