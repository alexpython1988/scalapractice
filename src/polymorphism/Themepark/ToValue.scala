package polymorphism.Themepark

//[] after class name will hold the type of the class

class ToValue[T] private (private var values: Array[Option[T]]) { // not allow new object -> user can only create object through apply
  
  def apply(hour: Int) = values(hour).get
  
  def get(hour: Int): Option[T] = values(hour)
  
  def update(hour: Int, v: T) = values(hour) = Some(v)
  
  def clear(hour: Int): Unit = {
    values(hour) = None
  }
  
//  def combine(o: ToValue[T], f: (Option[T], Option[T]) => Option[T]): ToValue[T] = {
//    val ret = ToValue[T]()
//    
//    for((v, i) <- values.zip(o.values).map(x => f(x._1, x._2)).zipWithIndex){
//       ret.values(i) = v
//    }
//    
//    return ret
//  }
  
   //add each param a type which allow working on different types -> need currying to fix type inference  
   def combine[B, C](o: ToValue[B])(f: (Option[T], Option[B]) => Option[C]): ToValue[C] = {
    val ret = ToValue[C]()
    
    for((v, i) <- values.zip(o.values).map(x => f(x._1, x._2)).zipWithIndex){
       ret.values(i) = v
    }
    
    return ret
  }
}

object ToValue extends App {
   // use apply to construct object
  def apply[T](): ToValue[T] = new ToValue(Array.fill(24)(None))
  
  def apply[T](a: T*): ToValue[T] = {
  val opts = a.map(x => Option(x)).toArray
    
  new ToValue[T](
    if(opts.length < 24){
      opts.padTo(24, None)
    }else if(opts.length > 24){
      opts.take(24)
    }else{
      opts
    })
  }
  
  //use second apply
  val r1 = ToValue[Int](1,2,3,4,5,6)
  //use first apply
  val r2 = ToValue[Int]()
  
  val w1 = ToValue[String]()
  val w2 = ToValue[String]()
  
  r1(12) = 6
  r1(8) = 10
  r2(14) = 8
  r2(12) = 5
  
  w1(12) = "alex"
  w1.values.foreach{println}
  
  
  val totalR = r1.combine(r2)((o1, o2) => (o1, o2) match {
    case (None, None) => None 
    case (Some(a), None) => Some(a)
    case (None, Some(b)) => Some(b)
    case (Some(a), Some(b)) => Some(a + b)  
    })
    
    totalR.values.foreach{println}
}






