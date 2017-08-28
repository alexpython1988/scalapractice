package networking

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import scalafx.application.JFXApp
import scalafx.scene.control.TextInputDialog
import scalafx.scene.Scene
import scalafx.scene.control.TextArea
import scalafx.scene.control.ListView
import scalafx.scene.control.TextField
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.BorderPane
import java.rmi.RemoteException
import scala.collection.mutable.ObservableBuffer
import scalafx.collections.ObservableBuffer
import scalafx.application.Platform

// make remote interface (traits-list of methods annotated with remote)
// object -> singleton extend trait and unitcastremoteobject to make it remote
// server bind itself to rmi registry
// look up server
// rmi registry run somewhere -> LocateRegistry -> createRegistry

@remote trait RemoteClient{
  def name: String
  def message(sender:RemoteClient, text:String): Unit
  def clientUpdate(clients: Seq[RemoteClient]): Unit
}

object RMIClient extends UnicastRemoteObject with RemoteClient with JFXApp{
   val dialog = new TextInputDialog("localhost")
   dialog.title = "Server Machine"
   dialog.contentText = "choose a server"
   dialog.headerText = "Server Name"
   
   val (_name, server) = dialog.showAndWait() match {
     case Some(machine) => 
       Naming.lookup(s"rmi://${machine}/ChatServer") match {
         case server: RemoteServer => 
            val dialog = new TextInputDialog("")
            dialog.title = "Chat name"
            dialog.contentText = "choose a name"
            dialog.headerText = "User Name"
            dialog.showAndWait() match {
              case Some(uname) => (uname, server)
              case None => sys.exit(0)
            }
//            val uname = dialog.showAndWait() 
//            if(uname.nonEmpty) (uname.get, server)
//            else sys.exit(0)
         case _ =>
           //(None, None)
           //println("Error")
           sys.exit(1)
         }
     case None => 
       sys.exit(1)
   }
   
   server.connect(this)
   var clients = server.getClients
   
   val chatArea = new TextArea
   chatArea.editable = false
   val userList = new ListView(clients.map{x => x.name})
   val chatField = new TextField
  
   chatField.onAction = (ae: ActionEvent) => {
     if(chatField.text().trim.nonEmpty){
       val recipients = if (userList.selectionModel().selectedItems.isEmpty){
         server.publicMessage(this, chatField.text().trim)
         clients
       }else{
         userList.selectionModel().selectedIndices.map{x => clients(x)}.toSeq
       }
       recipients.foreach{ r => 
         try {
           r.message(this, chatField.text().trim)
         } catch {
           case ex: RemoteException =>
             chatArea.appendText(s"Could not sent message to ${r.name}")
         }
       }
       chatField.text = ""
     }
   }
   
   stage = new JFXApp.PrimaryStage{
     title = "RMI Client"
     scene = new Scene(500, 500){
       val chatScroll = new ScrollPane
       chatScroll.content = chatArea
       val userScroll = new ScrollPane
       userScroll.content = userList
       val border = new BorderPane
       border.top = chatField
       border.left = userScroll
       border.center = chatScroll
       root = border
     }
   }
   
   def name: String = _name
   def message(sender:RemoteClient, text:String): Unit = {
     chatArea.appendText(sender.name + ": " + text + "\n")
     
   }
   
   def clientUpdate(clients: Seq[RemoteClient]): Unit = Platform.runLater{
     this.clients = clients
     if(userList != null) userList.items = ObservableBuffer(clients.map { x => x.name }
         )
   }
}