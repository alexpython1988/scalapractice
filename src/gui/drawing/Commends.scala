package gui.drawing

import stackandqueue.RPNCalc
import scalafx.application.Platform

//commends:
//echo: print
//add: give sum
//refresh
object Commends {
  private val commends = Map[String, (String, Drawing) => Any](
      "add" -> ((args, d) => args.trim().split(" +").map { x => x.toInt }.sum),
      "echo" -> ((args, d) => args.trim()),
      "refresh" -> ((args, d) => Platform.runLater(d.draw())),
      "rpn" -> ((args, d) => RPNCalc(args.trim().split(" +"), d.vars)),
      //set x to 3
      "set" -> ((args, d) => {
        val parts = args.trim().split(" +")
        d.setVar(parts(0), parts(1).toDouble)
        "set " + parts(0) + " to " + parts(1)
        }),
      "freeze" ->((args, d) => Thread.sleep(args.trim().toInt*1000))
    )
  
  def apply(input: String, drawing: Drawing): Any = {
    val spaceIndex = input.indexOf(" ")
    val(commend, args) = if(spaceIndex < 0)(input, "") else input.splitAt(spaceIndex)
    if(commends.contains(commend.toLowerCase())){
      commends(commend.toLowerCase())(args.trim, drawing)
    }else "Not valid commend!"
  }
}