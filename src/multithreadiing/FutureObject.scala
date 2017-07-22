package multithreadiing

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureObject extends App {
  val page1 = Future{
    "google" + io.Source.fromURL("http://www.google.com").take(100).mkString
  }
  
  val page2 = Future{
    "facebook" + io.Source.fromURL("http://www.facebook.com").take(100).mkString
  }
  
  val page3 = Future{
    "youtube" + io.Source.fromURL("http://www.youtube.com").take(100).mkString
  }
  
  val pages = List(page1, page2, page3)
  val firstPage = Future.firstCompletedOf(pages)
  firstPage.foreach { x => println(x) }
  
  val allPages = Future.sequence(pages)
  allPages.foreach{
    x => x.foreach { x => println(x) }
  }
  
  Thread.sleep(3000)
}