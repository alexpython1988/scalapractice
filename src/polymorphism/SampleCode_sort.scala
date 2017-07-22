package polymorphism

object SampleCode_sort {
  //sort for double
  def bubbleSort(arr: Array[Int]): Unit = {
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
  
  //sort function can work for many types (abstract and polymorphism)
  //inclusion (inheritance or sub-typing)
  
  
  def main(args: Array[String]): Unit = {
    val arr = Array(3,5,7,2,1,4,6)
    bubbleSort(arr)
    arr.foreach{println}
  }
}