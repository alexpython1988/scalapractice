package multithreadiing

import scala.concurrent.Future


class BankAccount (private var _balance: Int) {
  def balance = _balance
  
  def depostite(amount: Int): Boolean = this.synchronized {
    if(amount < 0)
      return false
    else{
      _balance += amount
      return true
    }
      
  }
  
  def withDraw(amount: Int): Boolean = this.synchronized {
    if(amount < 0 || amount > _balance){
      return false
    }else{
      _balance -= amount
      return true
    }
  }
}

object BankAccount extends App{
  val acc = new BankAccount(0)
  for(i <- (1 to  10000000).par){
    acc.depostite(1)
  }

  println(acc.balance)
}




