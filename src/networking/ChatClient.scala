package networking

import java.net.Socket
import java.io.PrintStream
import java.io.BufferedReader
import java.io.InputStreamReader
import scala.io.StdIn
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChatClient extends App {
  val skt = new Socket("127.0.0.1", 19888)
  println("establish connection via " + skt)
  val in  = new BufferedReader(new InputStreamReader(skt.getInputStream()))
  val out  = new PrintStream(skt.getOutputStream())
  
  //future thread die with main thread death
  Future{
    while(true){  
      println("from server> " + in.readLine())
    }
  }
  
  var input = ""
  while(input != ":q"){
    print("input> ")
    input = StdIn.readLine()
    out.println(input)
    println()
  }
  
  skt.close()
  println("done")
}