package basic.oop.studentgrade

object Grade {
  def main(args: Array[String]): Unit = {
    val students = List(new Student("aaa", "bbb", List(1,2,3), List(4,5,6), List(7,8,9)),new Student("ccc", "ddd"))
    
    val std1 = students.apply(1)
    for(i <- 10 to 12)
      std1.addQuiz(i)
    for(k <- 20 to 30 by 3)
      std1.addAssgn(k)
    for(j <- Array.fill(4)((math.random * 100).toInt))
      std1.addTest(j)
    
      students.foreach { x => printInfo(x) }
//    for(std <- students){
//      printInfo(std)
//    }
    
    
  }
  
  def printInfo(std: Student): Unit = {
    println(std.firstName + " " + std.LastName)
    println(s"average score is ${std.avg()}")
    println(s"Test = ${std.test}")
    println(s"quiz = ${std.quiz}")
    println(s"assignment = ${std.assgn}")
    println()
  }
}