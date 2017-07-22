package lib

/*
 * commons
 */

/**
 * this is the first object made
 */
object HelloWorld {
  /*
   * var can be reassgined to new value while val cannot be reassigned 
   * var = variable
   * val = final variable
   * 
   * first type val keyword
   * 	you can add type after the variable
   * sec type var keyword
   * 
   * types: String, Int, Double, Char, Boolean, Unit
   * 
   * var variable:type = value
   * 
   * == is equally comparison diff from java
   */
  val name:String = "alex" //val name = "alex" 
  val value = 42
  var age:Int = 22
  var unit = () //unit
  val t = (1, 2, 3, "hello") //tuple 
  val (a, b, c, d) = t
  
  //$varibale can be directly insert into the string; more complex expression need to be put inside {}, the string format as  s"xxxx $var xxxx ${exp}"
  var msg = name + " is " + age + " years old."
  //vary elegant way!!!! to use string interpolation
  var msg1 = s"$name is $age years old"
  var msg2 = s"the third element in t is ${t._3}"
  var msg3 = s"$name is ${age + 1} years old"
  
  def main(args: Array[String]): Unit = {
   println("hello world!!!")
   println(t._1)
   println(d)
   age = 12
   println(age)
   println(msg)
   println(msg1)
   println(msg2)
   println(msg3)
   
   //test1 return a lambda expression which take a number and return a square of this number
   var a = test1()
   println(a(12))
   
   //claim lambda as val instead of var; autoconvert int to double
   //val twice: Double => Double = _ * 2
   val twice: Double => Double = x => x * 2
   println(twice(1))
   
   //val It = (x : Double, y : Double) => x < y
   val It: (Double, Double) => Boolean =  _ < _
   println(It(2.1, 1.90))
   
   val thing = {
     val temp = 5
     print("inside block of thing, thing is ")
     temp
   }
   println(thing) //thing = temp = 5
   
   ifwhile()
   
   testMatch(20)
   
   testTryCatch()
  }
  
  //lambda
  def test1(): (Int => Int) = {
    // => define lambda
    val square = (x:Int) => x * x
    return square
  }
  
  //if while for
  def ifwhile(): Unit = {
    //while
    var i = 0
    while(i < 10){
      if(i % 2 == 0){
        print(i)
      }
        
      i += 1
    }
    
    println()
    
    //if else in scala way (always you can use if else as java)
    val resp = if(age < 19){
      s"smaller than $name"
    }else{
      s"larger or equal to $name"
    }
    
    println(resp)
    
    //for loop
    //for(j <- 1 to 10){
    //you can add if in for loop
    for(j <- 0 until 10; if j % 3 == 0 || j % 5 == 0; k <- 'a' to 'c'){
      print(s"$j : $k ")
      //nesting output
      //0 : a 0 : b 0 : c 3 : a 3 : b 3 : c 5 : a 5 : b 5 : c 6 : a 6 : b 6 : c 9 : a 9 : b 9 : c 
    }
    println()
    
   //another way to use for loop
    val forloop = for{
    j <- 0 until 10
    if j % 2 == 0 || j % 3 == 0
    k <- 'x' to 'z' 
    sqr = j * j  
    } yield {
      j -> k -> sqr
    }
    
    println(forloop) //forloop is a vector because of yield
  }

  //match
  def testMatch(bond : Int): Unit = {
    val fizzbuzz = for(i <- 0 to bond) yield {
//      if(i % 3 == 0 && i % 5 == 0) "fizzbuzz"
//      else if(i % 3 == 0) "fizz"
//      else if(i % 5 == 0) "buzz"
//      else i
     //use match to replace if else if else
     //make sure type is same for all case results because if types are diff the original value type becomes any
     (i % 3, i % 5) match {
       case (0, 0) => "fizzbuzz"
       case (0, _) => "fizz"
       case (_, 0) => "buzz"
       case _ => i.toString()      
     } 
    }
    println(fizzbuzz)
  }
  
  //try catch
  def testTryCatch(): Unit = {
    val str = "123a"
    val num = try {
      str.toInt
    }catch {
      //partial function
      //case t: Throwable => t.printStackTrace()
      case ex: NumberFormatException => Int.MinValue //make sure all case return same type you want for you expression
    }
    
    println(num)
  }
  
}












