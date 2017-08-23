package io

import java.io.FileInputStream
import java.io.DataOutputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.DataInputStream
import java.io.BufferedInputStream
import java.io.ObjectOutputStream
import java.io.ObjectInputStream

object LoanPattern {
  def withFileInputStream[A](fileName: String)(body:FileInputStream => A): A = {
    val fis = new FileInputStream(fileName)
    try {
      body(fis)
    }finally {
      fis.close()
    }
  }
  
  def withDataOutputStream[A](fileName: String)(body: DataOutputStream => A): A = {
    val dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))
    try {
      body(dos)
    }finally {
      dos.close()
    }
  }
  
  def withDataInputStream[A](fileName: String)(body: DataInputStream => A): A = {
    val dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))
    try{
      body(dis)
    }finally {
      dis.close()
    }
  }
  
  def withObjectOutputStream[A](fileName: String)(body: ObjectOutputStream => A): A = {
    val oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))
    try {
      body(oos)
    }finally {
      oos.close()
    }
  }
  
  def withObjectInputStream[A](fileName: String)(body: ObjectInputStream => A): A = {
    val ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))
    try{
      body(ois)
    }finally {
      ois.close()
    }
  }
}