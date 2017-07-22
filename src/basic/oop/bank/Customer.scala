package basic.oop.bank

class Customer (
    val firstName: String,
    val lastname: String,
    val cid: String,
    private var _addr: Address) 
{
  private var _accounts: List[Account] = Nil
  
  def addAccount(acct: Account): Unit = {
    _accounts ::= acct
  }
  
  def removeAccount(id: String): Boolean = {
    val index = _accounts.indexWhere(_.aid == id)
    
    if(index < 0)
      return false
    else{
      _accounts = _accounts.patch(index, Nil, 1)
      return true
    }
  }
  
  def accounts = _accounts
  
  def changeAddress(addr: Address): Unit = {
    _addr = addr
  }
  
  def address = _addr
  
  def printInfo(): Unit = {
    println(s"name is $firstName $lastname and id is $cid")
  }
}