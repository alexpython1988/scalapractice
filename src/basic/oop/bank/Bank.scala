package basic.oop.bank

class Bank {
  private var _customers: List[Customer] = Nil
  private var _accounts: List[Account] = Nil
  private var nextCustomerId = 0
   private var nextAccountId = 0
  
  def addCustomer(cust: Customer): Unit = {
    _customers ::= cust
  }
  
  def addCustomer(
      firstName: String, 
      lastName: String, 
      addr: Address): Customer = {
    nextCustomerId += 1
    val cust = new Customer(firstName, lastName, nextCustomerId.toString, addr)
    _customers ::= cust
    
    return cust
  }
  
  def openAccount(c: Customer): Account = {
    nextAccountId += 1
    val acct = new Account(c, nextAccountId.toString)
    _accounts ::= acct
    return acct
  }
  
  def closeAccount(a: Account): Boolean = {
    if(_accounts.contains(a)){
      _accounts = _accounts.filter { x => x != a }
      if(!a.customer.removeAccount(a.aid)){
        println("account was not in cutomer!!!")
      }
      return true
    }else{
      return false
    }
  }
  
  def closeAccount(aid: String): Boolean = {
    val index = _accounts.indexWhere { _aid => _aid == aid }
    
    if(index < 0){
      return false
    }else{
      val acct = _accounts.apply(index)
      if(!acct.customer.removeAccount(aid)){
        println("account was not in cutomer!!!")
      }
      _accounts.patch(index, Nil, 1)
      return true
    }
  }
  
  def findCustomer(fName: String, lName: String): Option[Customer] = {
    return _customers.find { c => c.firstName == fName && c.lastname == lName  }
  }
  
  def findCustomer(id: String): Option[Customer] = {
    return _customers.find(_.cid == id)
  }
  
  def findAccount(aid: String): Option[Account] = {
    return _accounts.find { x => x.aid == aid }
  }
}








