package networking

import java.net.ServerSocket
import java.io.PrintStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._

object ChatServer extends App {
  case class User(name: String, sock: Socket, in: BufferedReader, out: PrintStream)
  val users = new ConcurrentHashMap[String, User].asScala
  
  def checkConnection(): Unit = {
    val sskt = new ServerSocket(19888)
    println("Accepting...")
    while(true){
      val skt = sskt.accept()
      println("connect to " + skt)
      val in  = new BufferedReader(new InputStreamReader(skt.getInputStream()))
      val out  = new PrintStream(skt.getOutputStream())
      //add user 
      Future{
        out.println("what is you name?")
        val name = in.readLine()
        //need to handle same name case
        val user = User(name, skt, in, out)
        users += name -> user
        out.println("hello " + name)
      }
    }
  }
  
  def doChat(user: User): Unit = {
    nonBlockingRead(user.in).foreach{ input =>
      input match {
        case ":q" => 
          user.sock.close()
          users -= user.name
        case _  =>
          if(!input.contains("@")){
            for ((n, u) <- users){
              if(n != user.name) {
                u.out.println(user.name + " : " + input)
              }
            }
          }else{
            val target = input.split("@")(0)
            val msg = input.split("@")(1)
            if (users.contains(target)){
              users.get(target).get.out.println(msg)
            }else{
              println("target user is not exist online.")
            }
          }
      }
      
//      if(input == ":q") {
//        user.sock.close()
//        users -= user.name
//      }else{
//        for ((name, u) <- users){
//          if(name != user.name) {
//            u.out.println(name + " : " + input)
//           }
//         }
//      }
    }
  }
  
  def nonBlockingRead(in: BufferedReader): Option[String] = {
    if(in.ready()) Some(in.readLine()) else None
  }
  
  Future{ 
    checkConnection()
  }
  
  //run through users to check input
  while(true){
    for((name, user) <- users){
      doChat(user)
    }
    Thread.sleep(100)
  }
  
//  val inMsg = in.readLine()
//  println("recieve from client> " + inMsg)
//  out.println("200 OK")
//  sskt.close()
}