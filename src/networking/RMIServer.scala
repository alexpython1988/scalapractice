package networking

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import java.rmi.registry.LocateRegistry
import scala.collection.mutable
import java.rmi.RemoteException

@remote trait RemoteServer {
  def connect(client: RemoteClient): Unit
  def disconnect(client: RemoteClient): Unit
  def getClients: Seq[RemoteClient]
  def publicMessage(client: RemoteClient, text: String): Unit
}

object RMIServer extends UnicastRemoteObject with App with RemoteServer{
  LocateRegistry.createRegistry(1099)
  Naming.rebind("ChatServer", this)
  
  private val clients = mutable.Buffer[RemoteClient]()
  private var history = mutable.ListBuffer("Server Started \n")
  
  def connect(client: RemoteClient): Unit = {
    clients += client
    sendUpdate
    history.mkString("\n") + "\n"
  }
  
  def disconnect(client: RemoteClient): Unit = {
    clients -= client
    sendUpdate
  }
  
  def getClients: Seq[RemoteClient] = clients
  
  
  def publicMessage(client: RemoteClient, text: String): Unit = {
    history += client.name + ": " + text
    if(history.length > 10) history.remove(0)
    clients.foreach { x => x.message(client, text) }
  }
  
  private def sendUpdate: Unit = {
    val deadClients = clients.filter { c =>
      try{
        c.clientUpdate(clients)
        false
      }catch{
        case ex: RemoteException => true
      }
    }
    
    clients --= deadClients
    clients.foreach { x => x.clientUpdate(clients) }
  }
}




