package multithreadiing

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.Includes._

object ThreadGui extends JFXApp{
  stage = new JFXApp.PrimaryStage{
    title = "Event Thread Demo"
    scene = new Scene(150,75){
      val button = new Button("Click")
      root = button
      button.onAction = (ae: ActionEvent) => Thread.sleep(15000)
    }
  }
}