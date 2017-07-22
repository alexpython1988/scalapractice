package lib

import scala.io.Source
import scala.io.StdIn
import java.io.PrintWriter


//import scala.io.StdIn //stdin do not need to import

object TestLibIO {
  def main(args: Array[String]): Unit = {
//   testStdIn()
   testReadTextFile("matrix.txt")
   
  }
  
  def testStdIn(): Unit = {
    println("What is your name?")
   // ??? //nothing you have to implement later, give compile error
    val name = StdIn.readLine()
    
    println("How old are you?")
   // ???
    val age =  StdIn.readInt()
    
    println(s"name is $name and age is $age")
  }
  
  def testReadTextFile(url : String): Unit = {
    val source = Source.fromFile(url)
    val lines = source.getLines()
//    for(line <- lines){
//      println(line)
//    }
//    
//    val matrix = lines.map{line => line.split(" ").map{x => x.toDouble}}.toArray
    val matrix = lines.map(_.split(" ").map(_.toDouble)).toArray
    matrix.foreach { x => x.foreach { println }}
    
    source.close()
    
    val pw = new PrintWriter("rowsum.txt")
    matrix.foreach{row => pw.println(row.sum)}
    
    val pw1 = new PrintWriter("rowmax.txt")
    matrix.foreach{row => pw1.println(row.max)}
    
    pw.close()
    pw1.close()
  }
}















