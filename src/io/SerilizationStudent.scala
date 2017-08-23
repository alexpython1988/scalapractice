package io
import LoanPattern._
import util.Random
import java.io.ObjectOutputStream
import java.io.ObjectInput
import java.io.ObjectInputStream

class Student(val name:String, val grade: Array[Int], @transient private var _od: OtherData) extends Serializable{
  def od: OtherData = {
    if (_od == null) {
      _od = new OtherData("", "")
    }
    _od
  }
  
  //customize the object read and write to include attr that cannot be serialized
  private def writeObject(oos: ObjectOutputStream): Unit = {
    oos.defaultWriteObject()
    oos.writeUTF(_od.id)
    oos.writeUTF(_od.major)
  }
  
  private def readObject(ois: ObjectInputStream): Unit = {
    ois.defaultReadObject()
    _od = new OtherData(ois.readUTF(), ois.readUTF())
  }
}

class OtherData(val id:String, val major: String)

object SerilizationStudent extends App {
  val od = new OtherData("1", "cs")
  val s = new Student("jim", Array.fill(5)(Random.nextInt(100)), od)  
  
  withObjectOutputStream("student.bin"){oos => 
    oos.writeObject(s)  
  }
  
  println(s.name)
  println(s.grade.mkString(","))
  println("output done")
  
  val s1 = withObjectInputStream("student.bin"){ois =>
    ois.readObject() match {
      case s: Student => s
      case _ => throw new RuntimeException("file did not contain a student.") 
    }
  }
  
  println(s1.name)
  println(s1.grade.mkString(","))
  println(s1.od.id)
  println(s1.od.major)
}