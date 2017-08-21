package io

import java.io.FileInputStream
import java.io.IOException
import java.io.FileNotFoundException

object ReadBytes extends App {
  def useFileInputStream[A](fileName: String)(body: FileInputStream => A): A = {
    val fis = new FileInputStream(fileName)
    try{
      body(fis)
    }finally{
      fis.close()
    }
  }
  
  try{
    ReadBytes.useFileInputStream("src/io/ReadBytes.scala"){
      fis => 
       var byte = fis.read()
       while (byte > 0){
          print(byte + " ")
          byte = fis.read()
       }
       println()
    }
  }catch {
    case e: IOException =>
      e.printStackTrace()
    case e: FileNotFoundException =>
      e.printStackTrace()
  }
   
  
}