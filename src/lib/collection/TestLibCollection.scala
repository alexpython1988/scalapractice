package lib.collection

import scala.io.StdIn

object TestLibCollection {
  def main(args: Array[String]): Unit = {
//    testArrayAndList()
//
//    testFillAndTabulate()
//
//    testMethods()
//
//    testHighOrderMethods()
//
//    testStrings()
//
//    val list = buildList()
//    println(list)
    
//    val str = concatString(List("alex", "is", "a", "human"))
//    println(str)
    
//    val str1 = concatStringPattern(List("one", "two", "three"))
//    println(str1)
      
//    testOption() 
    grade(quiz = List(45,98), assignment = List(87, 69),  test = List(100))
    
    //use carry
    val plus3 = add(3)_
    val five = plus3(2)
    println(five)
    
    println(threeTuple0(math.random))//(0.8174438778427611,0.8174438778427611,0.8174438778427611)
    println(threeTuple1(math.random))//(0.7413044994321009,0.5627662976858467,0.5789757055369087)  
  }
  
  //pass function as argument as function name
  //in func0 program evaluate math.random first then pass the value to a
  def threeTuple0(a:Double):(Double, Double, Double) = {
    return (a, a, a)
  }
  
  //int func1 program path the name of the random function as param a then, when return a has been evaluated
  //a here is a function with all the code in this function
  def threeTuple1(a: => Double):(Double, Double, Double) = {
    return (a, a, a)
  }
  
  //curry
  def add(x:Int)(y:Int): Int = x + y
  
  def grade(quiz: List[Int], test: List[Int] = Nil, assignment: List[Int]): Double = {
    return (quiz.sum + test.sum + assignment.sum) / (quiz.length + test.length + assignment.length) * 1.0
  }
  
  //Option type
  def testOption(): Unit = {
    val arr = Array(1,2,3,4,5,6,7,8,9)
    val res = arr.find(_ < 5)
    println(res)
    val res1 = arr.find(_ > 10)
    println(res.getOrElse())
    println(res1.getOrElse())
    
    val f = res match {
      case None => "Not find"
      case Some(i) => s"return $i"
    }
    
    val f1 = res1 match {
      case None => "Not find"
      case Some(i) => s"return $i"
    }
    
    println(f)
    println(f1)
  }
  
  def concatStringPattern(words: List[String]): String = words match {
    case Nil => ""
    // h :: t pattern means h is head of words and t is the tail
    case h :: t => h + concatStringPattern(t)
  }
  
  def concatString(words: List[String]): String = {
    if(words.isEmpty){
     return ""
    }else {
     return words.head + concatString(words.tail) 
    }
  }
  
  //tail recursively build list
  def buildList(): List[String] = {
    println("input something (quit to quit): ")
    val input = StdIn.readLine()
    
    //use if else
//    if(input == "quit"){
//      return Nil
//    }else{
//      return input :: buildList()
//    }
    
    //use match
    input match {
      case "quit" => Nil
      case _ => input :: buildList()
    }
  }
  
  def testStrings(): Unit= {
    val str = "This is a test"
    println(str.filter { x => x < 'l' })
    val strs = str.split(" ")
    strs.foreach { println }
    println()
  }
  
  def testHighOrderMethods(): Unit = {
    val arr = Array(1,2,3,5,6,10)
    //map apply map function to all elements return new array
    arr.map { x => x * 2 }.foreach { print }
    println()
    arr.map { _ * 2 }.foreach { print }
    println()
    //filter apply condition to all elements and keep the ones that meet the requirements return new array
    arr.filter { x => x < 5 }foreach { print }
    println()
    arr.filter { _ < 5 }foreach { print }
    println()
    val b = arr.exists { x => x > 5 && x % 2 == 0 }
    val c = arr.count { x => x > 5 && x % 2 == 0 }
    println(s"$b   $c")
    
    val arr1 = Array("carrier", "bu", "alex", "dog")
    println(arr1.min)
    println(arr1.minBy { _.length() })
    
    println()
  }
  
  def testMethods(): Unit = {
    val arr = Array(1,2,3,5,6,10)
    println(arr.head)
    arr.tail.foreach { print }
    println()
    println(arr.last)
    arr.drop(2).foreach { print } // return new list old list not change
    println()
    arr.dropRight(2).foreach { print } // return new list old list not change
    println()
    println(arr.length)
    println(arr(arr.length - 1))
    //arr.splitAt(2) == arr.take(2) and arr.drop(2)
    arr.takeRight(2).foreach { print }
    println()
    arr.slice(2, 5).foreach { print }
    println()
    //patch(patch start index, patch content, how many elements in original has to be removed)
    arr.patch(1, Array(100,101), 1).foreach { print }
    println()
    println(arr.mkString("-"))
    arr.zip(Array("ab", "bc", "cd", "de")).foreach { print }
    println()
    println()
  }
  
  def testFillAndTabulate(): Unit = {
    //fill only give value, tabulate give expression based on index
    
    val arr = Array.fill(10)(math.random)
    arr.foreach { println }
    
    val list = List.fill(5)(StdIn.readLine())
    list.foreach{println}
    
    val arr1 = Array.tabulate(10)(i => i * 2)
    arr1.foreach { println }
    
    println()
  }
  
  def testArrayAndList(): Unit = {
    val arr = Array(1,2,3,4,5)
    val list = List("alex", "alex1", "alex2")
    
    //array is mutable
    arr(1) = 6
    for(i <- arr){
      println(i) //16345 2 is replaced by 6
    }
    
    //list is not mutable
    
    //array size is not changeable
    //list size can easily change and we can append to head of list instead of end (return new list)
    val list1 = list.::("alex0") //give new list
    //val list1 = "alex00" :: list
    for(j <- list1){
      println(j)
    }
    
    //foreach iteration
    list1.foreach {println}
    arr.foreach { x:Int => println(x * x) }
    //arr.foreach { println }
    println()
  }
}