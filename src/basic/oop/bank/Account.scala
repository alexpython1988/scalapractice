package basic.oop.bank

class Account (
    val customer: Customer, 
    val aid: String)
{
  private[this] var _balance = 0 //more safe, since other account instance cannot alter balance of others
  
  //automatically add newly created account to the customer
  customer.addAccount(this)
  
  def balance = _balance
  
  //def function name with an attribute with "_=" => define a set method => re-assign value to current field 
  def balance_=(b: Int): Unit = {
    if(b < _balance)
      withDraw(_balance - b)
    else
      depostite(b - _balance)
  }
  
  def depostite(amount: Int): Boolean = {
    if(amount < 0)
      return false
    else{
      _balance += amount
      return true
    }
      
  }
  
  def withDraw(amount: Int): Boolean = {
    if(amount < 0 || amount > _balance){
      return false
    }else{
      _balance -= amount
      return true
    }
  }
}

object Account {
  def main(args: Array[String]): Unit = {
    val acct = new Account(new Customer("a", "b", "2", new Address(Nil)), "1")
    acct.balance = 50
    println(acct.balance)
    acct.balance += 40 // => evaluate acct.balance + 40 then evaluate balance_=(b) method
    println(acct.balance)
    acct.balance -= 20
    println(acct.balance)
  }
}