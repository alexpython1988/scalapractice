package basic.oop.studentgrade

import scala.BigDecimal

//main only in Object, avoid use main in class in scala
//student is mutable since quiz, assgn and test can be changed
class Student (
    //field
    val firstName: String, 
    val LastName: String, 
    private var _quiz: List[Int] = Nil, 
    private var _assgn: List[Int] = Nil, 
    private var _test: List[Int] = Nil) 
    //methods body
{
  def quiz = _quiz.reverse
  def test = _test.reverse
  def assgn = _assgn.reverse
  
  private def checkGrade(grade: Int): Boolean = {
    return grade >= 0 && grade <= 100
  }
  
  def addQuiz(grade: Int): Boolean = {
    if(checkGrade(grade)){
      _quiz ::= grade
      return true
    }else{
      return false
    }
  }
  
  def addTest(grade: Int): Boolean = {
    checkGrade(grade) match {
      case true => {_test ::= grade; return true}
      case false => {return false}
    }
  }
  
   def addAssgn(grade: Int): Boolean = {
    if(checkGrade(grade)){
      _assgn ::= grade
      true
    }else{
      false
    }
  }
  
  def avg(): Double = {
    return BigDecimal(quizAvg() * 0.1 + testAvg() * 0.4 + assignmentAvg() * 0.5)
             .setScale(2, BigDecimal.RoundingMode.HALF_UP)
             .toDouble
  }
  
  def quizAvg(): Double = {
    if(_quiz.isEmpty){
      return 0.0
    }else if(_quiz.length == 1){
        return _quiz.head.toDouble
    }else{
      //drop lowest one
      return (_quiz.sum - _quiz.min).toDouble / _quiz.length 
    }
  }
  
  def testAvg(): Double = {
    if(_test.isEmpty){
      return 0.0
    }else{
      return _test.sum.toDouble / _test.length 
    }
  }
  
  def assignmentAvg(): Double = {
    if(_assgn.isEmpty){
      return 0.0
    }else{
      return _assgn.sum.toDouble / _assgn.length 
    }
  }
  
}

