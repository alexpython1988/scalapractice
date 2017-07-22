package polymorphism

object Sorting extends App{
  
  //param is ordered subclass or type can be infered as ordered
  def bubbleSort[T <% Ordered[T]](arr: Array[T]): Unit = {
    for(i <- 0 until arr.length){
      for(j <- (i - 1) to 0 by -1){
        if(arr(j) > arr(j + 1)){
          val temp = arr(j)
          arr(j) = arr(j +1)
          arr(j + 1) = temp
        }
      }
    }
  }
  
  //currying // sortWith method
  def bubbleSortPrime[T](arr: Array[T])(lt: (T, T) => Boolean): Unit = {
  for(i <- 0 until arr.length){
      for(j <- (i - 1) to 0 by -1){
         if(lt(arr(j), arr(j + 1))){
          val temp = arr(j)
           arr(j) = arr(j +1)
           arr(j + 1) = temp
         }
       }
     }
   }
  
  //test
  val arr = Array.fill(10)(util.Random.nextInt(100))
  bubbleSort(arr)
  arr.foreach{println}
  println()   
   
  val arr1 = Array.fill(10)(math.random)
  bubbleSort(arr1)   
  arr1.foreach{println}
  println()
   
  //test
  bubbleSortPrime(arr)(_ < _)  // "<" sort from large to small; ">" sort from small to large
  arr.foreach{println}
  println()   

}