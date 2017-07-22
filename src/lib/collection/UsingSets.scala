package lib.collection
import scala.collection.mutable

object UsingSets extends App {
  val year  = 2015
  val nationalData = {
    val source = io.Source.fromFile(s"names/yob$year.txt")
    val names = source.getLines().
                       filter { x => x.nonEmpty }.
                       map(_.split(",")(0)).toSet
    source.close()
    names
  }
  
  //println(nationalData)
  
//  for(stateFile <- new java.io.File("names").list(); if stateFile.endsWith(".TXT")){
//    val stateData = {
//      val source = io.Source.fromFile("names/" + stateFile)
//      var s: mutable.Set[String] = mutable.Set()
//      val names = source.getLines().
//                         filter(_.nonEmpty).
//                         map(_.split(",")).
//                         filter{ a => a(2).toInt == year }.
//                         map { a => List(a(3), a(0)) }
//
////      val source1 = io.Source.fromFile("names/" + stateFile)
////      val st = source1.getLines().
////               filter(_.nonEmpty).
////               map(_.split(",")).
////               filter{ a => a(2).toInt == year }.
////               map { a => a(0) }.toArray
//      var count = 0
//                         
//      for(each <- names){
//        s.add(each.apply(1))
//        if(nationalData.contains(each.apply(0))){
//          count += 1
//        }
//      }
////      val count = names.count(n => nationalData.contains(n.apply(0)))
//     
//      source.close()
////      source1.close()
//      List(s, count)
//      //count
//    }
//    
//    stateData.apply(0).asInstanceOf[mutable.Set[String]].foreach(println)
//    println(stateData.apply(1).asInstanceOf[Int].toDouble / nationalData.length)
//    //println(stateData.toDouble / nationalData.length)
//  }

  
   val info = for(stateFile <- new java.io.File("names").list(); if stateFile.endsWith(".TXT")) yield {
     val source = io.Source.fromFile("names/" + stateFile)
     val names = source.getLines().filter { x => x.nonEmpty }.
                         map { x => x.split(",") }.
                         filter { x => x(2).toInt == year }.
                         map { x => x(3) }.toArray
     source.close()
     (stateFile.take(2), names)
   }
   
   for((state, snames) <- info){
     println(state + "   " + snames.count { x => nationalData.contains(x) })
   }
}













