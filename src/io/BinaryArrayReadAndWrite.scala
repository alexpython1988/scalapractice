package io
import LoanPattern._

object BinaryArrayReadAndWrite extends App {
  val arr = Array.fill(10)(math.random)
  withDataOutputStream("arr.bin"){dos => 
    dos.writeInt(arr.length)
    //arr.foreach(dos.writeDouble)
    arr.foreach{
      x => dos.writeDouble(x)
    }
  }
  
  print(arr.mkString(", "))
  println()
  println("done")
  
  val arr1 = withDataInputStream("arr.bin"){dis =>
    Array.fill(dis.readInt())(dis.readDouble())
  }
  
  print(arr1.mkString(", "))
}