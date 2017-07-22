package polymorphism.enumerate

import com.sun.xml.internal.ws.wsdl.writer.document.Import

object StreetLightColor extends Enumeration{
  //type are Int
//  val red = 0
//  val green = 1
//  val yellow = 2
  
  //enum
  val red, green, yellow = Value
}

class StreetLight(private var _color: StreetLightColor.Value){
  def color = _color

  def circle: Unit = _color = _color match {
    case StreetLightColor.red => StreetLightColor.green
    case StreetLightColor.green => StreetLightColor.yellow
    case StreetLightColor.yellow => StreetLightColor.red
    case _ => throw new RuntimeException("invalid")
  }
}