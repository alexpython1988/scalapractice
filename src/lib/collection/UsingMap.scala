package lib.collection

import scala.io.StdIn

object UsingMap extends App {
  case class NameInfo(gender: Char, year: Int, name: String, count: Int)
  
  val stateInfo = for(stateFile <- new java.io.File("names").list(); if stateFile.endsWith(".TXT")) yield {
     val source = io.Source.fromFile("names/" + stateFile)
     val info = source.getLines().filter { x => x.nonEmpty }.
                         map { line => 
        val p = line.split(",")
        NameInfo(p(1)(0), p(2).toInt, p(3), p(4).toInt)
     }.toArray.groupBy { x => x.name }                   
                  
     source.close()
     (stateFile.take(2), info)
  }
  
  for((state, info) <- stateInfo.take(2)){
      println(state)
      for((k,v) <- info){
        println(k)
        v.foreach { x => println(s"${x.year} and ${x.count}") }
      }
    }
  
  private var flag = true
  while (flag) {
    println("input a name:")
    val name = StdIn.readLine().capitalize
    if(name == "Quit")
      flag = false
    else{
        for((state, info) <- stateInfo){
        println(state + "   " + info(name).maxBy { x => x.count })
      }
    }
  }
}