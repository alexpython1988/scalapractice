package basic.oop.bank

import scala.io.StdIn

object BankMain {

  private val menu = """select a option listed below:
  1. create a customer
  2. select a customer
  3. create an account
  4. close an account
  5. select an account
  6. deposite
  7. withdraw
  8. check account balance
  9. change address
  10 quit
  """
  
  def main(args: Array[String]): Unit = {
    val bank = new Bank()
    var op: Int = 0
    var customer: Option[Customer] = None
    var account: Option[Account] = None
    
    while(op != 10){
      println(menu)
      op = StdIn.readInt()
      
      op match {
        case 1 => {customer = Some(createCustomer(bank))}
        case 2 => {customer = selectCustomer(bank); customer.foreach { x => x.printInfo }}
        case 3 => {customer.map { c => createAccount(bank, c) }}
        case 4 => {
                   account.map { a => closeAccount(bank, a)}; 
                   account = None
          }
        case 5 => {account = selectAccount(bank)}
        case 6 => {account.foreach(deposite)} // deposite function must take account as parameters
        case 7 => {account.foreach { x => withdraw(x) }}
        case 8 => {account.foreach { x => println(s"The balance is ${x.balance}.") }}
        case 9 => {customer.foreach { changeAddress }}
        case 10 => {println("GoodBye!")}
        case _ => "Invalid option"
      }
    }
  }
  
  private def createAccount(bank: Bank, c: Customer): Account = {
    println("open account...")
    return bank.openAccount(c)
  }
  
  private def closeAccount(bank: Bank, a: Account): Unit = {
    if(bank.closeAccount(a)){
      println("account closed.")
    }else{
      println("internal error! account not closed.")
    }
  }
  
  private def changeAddress(c: Customer): Unit = {
    println("input new address:")
    c.changeAddress(readAddress())
  }
  
  private def deposite(a: Account): Unit = {
    println("input amount to deposite: ")
    val amount = StdIn.readInt()
    if(a.depostite(amount)){
      println(s"deposite $amount dollers.")
    }else{
      println("Operation failed. You need to repeat process.")
    }
  }
  
  private def withdraw(a: Account): Unit = {
    println("input amount to withdraw: ")
    val amount = StdIn.readInt()
    if(a.withDraw(amount)){
      println(s"withdrawing $amount dollers...")
    }else{
      println("Operation failed. You need to repeat process.")
    }
  }
  
  private def selectAccount(bank: Bank):  Option[Account] = {
    println("input your account id: ")
    val aid = StdIn.readLine()
    return bank.findAccount(aid)
  }
  
  private def createCustomer(bank: Bank): Customer = {
    println("input first name:")
    val fName = StdIn.readLine()
    println("input last name:")
    val lName = StdIn.readLine()
    println("input address:")
    val addr = readAddress()
    return bank.addCustomer(fName, lName, addr)
  }
  
  private def readAddress(): Address = {
//    var line: String = ""
//    var lines: List[String] = Nil
//    
//    line = StdIn.readLine()
//    while (line != "end") {
//      lines ::= line
//    }
//    
//    return new Address(lines.reverse)
    
     var lines = List[String]()
     do{
       lines ::= StdIn.readLine()
     }while(lines.head.nonEmpty)
     
     return new Address(lines.tail.reverse)
  }
  
  
  private def selectCustomer(bank: Bank): Option[Customer] = {
    println("input first and last name (seperate by a space) or id: ")
    val input = StdIn.readLine()
   
    if(input.contains(" ")){
      val name = input.split(" ")
      return bank.findCustomer(name(0), name(1))
    }else{
      return bank.findCustomer(input)
    }
  }
}








