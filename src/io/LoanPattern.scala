package io

import java.io.FileInputStream
import java.io.DataOutputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.DataInputStream
import java.io.BufferedInputStream

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
}