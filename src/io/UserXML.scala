package io

object UserXML extends App {
  val name = "Mark"
  val speed = 43
  val xmldata = <tag name={name} speed={speed.toString()}> This is an XML literal</tag>
  
  val inData = xml.XML.loadFile("drawing.xml")
  println(inData)
  
  val root = inData \ "drawable"
  //root \ "@value2" return type of NodeSeq, you can convert as follow
  val v1 = (root \ "@value2").text.toDouble
  println(v1)
  println((root \\ "@x" ).mkString(","))
}