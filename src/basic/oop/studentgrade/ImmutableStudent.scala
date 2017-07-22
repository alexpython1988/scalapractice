package basic.oop.studentgrade

case class ImmutableStudent (
   //field
    val firstName: String, 
    val LastName: String, 
    val quiz: List[Int] = Nil, 
    val assgn: List[Int] = Nil, 
    val test: List[Int] = Nil) 
    //methods body
{
  //add grade to quiz 
  def addQuiz(grade: Int) : ImmutableStudent = {
     if(checkGrade(grade)){
//       return new ImmutableStudent(
//           firstName, 
//           LastName,
//           grade :: quiz,
//           assgn,
//           test)
       return this.copy(quiz = quiz :+ grade)
     }else{
       return this
     }
  }
  
  def avg(): Double = {
    return quizAvg() * 0.1 + testAvg() * 0.4 + assignmentAvg() * 0.5
  }
  
  def quizAvg(): Double = {
    if(quiz.isEmpty){
      return 0.0
    }else if(quiz.length == 1){
        return quiz.head.toDouble
    }else{
      //drop lowest one
      return (quiz.sum - quiz.min).toDouble / quiz.length 
    }
  }
  
  def testAvg(): Double = {
    if(test.isEmpty){
      return 0.0
    }else{
      return test.sum.toDouble / test.length 
    }
  }
  
  def assignmentAvg(): Double = {
    if(assgn.isEmpty){
      return 0.0
    }else{
      return assgn.sum.toDouble / assgn.length 
    }
  }
  
  private def checkGrade(grade: Int): Boolean = {
    return grade >= 0 && grade <= 100
  }
}