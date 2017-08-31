package gui.drawing

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.control.MenuBar
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuItem
import scalafx.scene.control.SeparatorMenuItem
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TabPane
import scalafx.scene.control.Tab
import scalafx.scene.control.SplitPane
import scalafx.scene.control.ScrollPane
import scalafx.geometry.Orientation
import scalafx.scene.control.Slider
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.TextField
import scalafx.scene.control.TextArea
import scalafx.event.ActionEvent
import scalafx.scene.control.Label
import scalafx.scene.control.ChoiceDialog
import scalafx.scene.control.TreeView
import scalafx.scene.control.TreeItem
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform
import scalafx.stage.FileChooser
import io.LoanPattern._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import java.net.ServerSocket
import java.io.ObjectInputStream
import java.io.BufferedInputStream
import scalafx.scene.control.TextInputDialog
import java.net.Socket
import java.io.ObjectOutputStream
import java.io.BufferedOutputStream

object DrawingMain extends JFXApp{
  private var drawings = List[(Drawing, TreeView[Drawable])]()
  
  private val creators = Map[String, Drawing => Drawable](
        "Rectangle" -> (d =>  new DrawRectangle(0, 0, 100, 100, Color.Black, d)),
        "Transform" -> (d => new DrawTransform(d)),
        "Text" -> (d => new DrawText(200, 200, "The Text", Color.Black, d)),
        "maze" -> (d => new DrawMaze(d))
      )
  val tabPane = new TabPane
  stage = new JFXApp.PrimaryStage{ //anonemous class
    title = "Drawing Program"
    scene = new Scene(800, 600){
//      val button = new Button("click me")
//      button.layoutX = 100
//      button.layoutY = 100
//      val  rect = Rectangle(400, 200, 100, 150)
//      rect.fill = Color.AliceBlue
//      content = List(button, rect)
      
      val menuBar = new MenuBar
      val fileMenu = new Menu("File")
      val newItem = new MenuItem("New")
      val openItem = new MenuItem("Open")
      val saveItem = new MenuItem("Save")
      val exitItem = new MenuItem("Exit")
      fileMenu.items = List(newItem, openItem, saveItem, new SeparatorMenuItem, exitItem)
        
      val editMenu = new Menu("Edit")
      val addItem = new MenuItem("Add Drawable")
      val copyItem = new MenuItem("Copy")
      val pasteItem = new MenuItem("Paste")
      val cutItem = new MenuItem("Cut")
      editMenu.items = List(addItem, copyItem, pasteItem, cutItem)
      
      val collaborateMenu = new Menu("Collaborate")
      val acceptItem = new MenuItem("accept drawings")
      val sendItem = new MenuItem("send drawing")
      val joinItem = new MenuItem("join collaboration")
      collaborateMenu.items = List(acceptItem, sendItem, joinItem)
      
      menuBar.menus = List(fileMenu, editMenu, collaborateMenu)
      
      addDrawing(new Drawing, "Untitled")
//      val (tab, tree) = createTabs(firstDrawing, "Untitled")
//      drawings = drawings :+ firstDrawing -> tree
//      tabPane += tab
      
      newItem.onAction = (ae: ActionEvent) => {
        addDrawing(new Drawing, "Untitled")
//        val (tab, tree) = createTabs(firstDrawing, "Untitled")
//        drawings  = drawings :+ newDrawing -> tree
//        tabPane += tab
      }
      
      openItem.onAction = (ae: ActionEvent) => {
        val chooser = new FileChooser{
            title = "Open Drawing"
          }
          
        val file = chooser.showOpenDialog(stage)
        val fn = file.getName
        if(file != null){
          val drawing = if(fn.endsWith(".xml")){
            val xmlData = xml.XML.loadFile(fn)
            Drawing(xmlData)
          }else{
            withObjectInputStream(file.getAbsolutePath){ ois =>
              ois.readObject() match {
                case d: Drawing => 
                  println(d)
                  d
                case _ =>
                  new Alert(AlertType.Information){
                    title = "Bad content"
                    headerText = "Load Fail"
                    contentText = s"No drawing is found in file: ${fn}."
                  }.showAndWait()
                  null  
              }
            }
          }
          
          if(drawing != null){
            //println(1)
            addDrawing(drawing, fn)
//            val (tab, tree) = createTabs(drawing, fn)
//            drawings  = drawings :+ drawing -> tree
//            tabPane += tab
//            drawing.draw()
          }
        }
      }
      
      saveItem.onAction = (ae: ActionEvent) => {
        val current = tabPane.selectionModel().selectedIndex()
        
        if(current >= 0){
          val (drawing, _) = drawings(current)
          val chooser = new FileChooser{
            title = "Save Drawing"
          }
          
          val file = chooser.showSaveDialog(stage)
          if(file != null){
            if(file.getName.endsWith(".xml")){
              val xmlData = drawing.toXML
              xml.XML.save(file.getAbsolutePath, xmlData)
            }else{
              withObjectOutputStream(file.getAbsolutePath){ oos =>
                oos.writeObject(drawing)
              }
            }
            
            tabPane.tabs(current).text = file.getName
          }
        }
      }
      
      addItem.onAction = (ae: ActionEvent) => {
        val current = tabPane.selectionModel().selectedIndex()
        
        if(current >= 0){
          val (drawing, treeView) = drawings(current)
          val dTypes = creators.keys.toSeq
          val dialog = new ChoiceDialog(dTypes(0), dTypes)
          
          dialog.showAndWait().foreach {
            s => val d = creators(s)(drawing)
//              val d: Drawable = s match {
//              case "Rectangle" => new DrawRectangle(0, 0, 100, 100, Color.Black, drawing)
//              case "Transform" => new DrawTransform(drawing)
//              case "Text" => new DrawText(200, 200, "The Text", Color.Black, drawing)
//            }
          
           val treeSelect = treeView.selectionModel().selectedItem()
          
           def treeAdd(item: TreeItem[Drawable]): Unit = item.getValue match {
              case dt: DrawTransform =>
                dt.addChild(d)
                item.children += new TreeItem(d)
                drawing.draw()
              case d => treeAdd(item.getParent)
            }
          
            if(treeSelect != null){
              treeAdd(treeSelect)
            }
          }
        }
      }
      
      exitItem.onAction = (ae: ActionEvent) => {
        sys.exit(0)
      }
      
      acceptItem.onAction = (ae:ActionEvent) => {
        startServer()
        acceptItem.disable = true
      }
      
      sendItem.onAction = (ae:ActionEvent) => {
        sendDrawing()
      }
       
      joinItem.onAction = (ae:ActionEvent) => {
        
      }
      
      val rootPane = new BorderPane
      
      rootPane.top = menuBar
      rootPane.center = tabPane
      root = rootPane
    }    
  }
  
  private def createTabs(drawing: Drawing, title: String): (Tab, TreeView[Drawable]) = {
    val drawingTree = new TreeView[Drawable]
    drawingTree.root = drawing.makeTree()
    
    val treeScroll = new ScrollPane
    //binding <== one way <==> two way
    drawingTree.prefWidth <== treeScroll.width
    drawingTree.prefHeight <== treeScroll.height
    treeScroll.content = drawingTree
    
    val propertyPane = new ScrollPane
    
    val leftSplit = new SplitPane
    leftSplit.orientation = Orientation.Vertical
    leftSplit.items ++= List(treeScroll, propertyPane)
    
    val topRightBorder = new BorderPane
    val slider = new Slider(0, 1000, 0)
    val canvas = new Canvas(1000, 1000)
    val gc = canvas.graphicsContext2D
    drawing.gc = gc
    val scrollCanvas = new ScrollPane
    scrollCanvas.content = canvas
    topRightBorder.top = slider
    topRightBorder.center = scrollCanvas
    
    val bottomRightBorder = new BorderPane
    val commendField = new TextField
    val commendArea = new TextArea
    commendArea.editable = false
    bottomRightBorder.top = commendField
    bottomRightBorder.center = commendArea
    
    val rightSplit = new SplitPane
    rightSplit.orientation = Orientation.Vertical
    rightSplit.items ++= List(topRightBorder, bottomRightBorder)
    rightSplit.dividerPositions = 0.7
    
    //handle commends
    commendField.onAction = (ae:ActionEvent) => {
      val commendText =  commendField.text()
      //make commend process multithreading
      val commendRes = Future{
        Commends(commendText, drawing)
      }
      
      commendRes.foreach { x => 
        Platform.runLater(commendArea.appendText("> " + commendText + "\n" + x + "\n")) 
      }
      
      commendField.text = ""
    }
    
    val topSplit = new SplitPane
    topSplit.items ++= List(leftSplit, rightSplit)
    topSplit.dividerPositions = 0.3
    
    drawingTree.selectionModel.value.selectedItem.onChange {
      val selected = drawingTree.selectionModel().selectedItem()
      
      if(selected != null){
        propertyPane.content = selected.getValue.propertiesPane
      }else{
        propertyPane.content = new Label("Nothing Selected")
      }
    }
    
    val tab = new Tab
    tab.text = title
    tab.content = topSplit
    return tab -> drawingTree
  }
  
  private[drawing] def labeledTextField(labelString: String, initialText: String, action: String => Unit): BorderPane = {
      val borderPane = new BorderPane
      borderPane.left = new Label(labelString)
      val textField = new TextField
      textField.text = initialText
      borderPane.center = textField
      textField.onAction = (ae: ActionEvent) => action(textField.text.value)
      textField.focused.onChange(if(!textField.focused.value) action(textField.text.value))
      return borderPane
  }
  
//  private def createMenuBar(): List[Menu] = {
//     val fileMenu = new Menu("File")
//     val newItem = new MenuItem("New")
//     val openItem = new MenuItem("Open")
//     val saveItem = new MenuItem("Save")
//     val exitItem = new MenuItem("Exit")
//     fileMenu.items = List(newItem, openItem, saveItem, new SeparatorMenuItem, exitItem)
//      
//     val editMenu = new Menu("Edit")
//     val addItem = new MenuItem("Add Drawable")
//     val copyItem = new MenuItem("Copy")
//     val pasteItem = new MenuItem("Paste")
//     val cutItem = new MenuItem("Cut")
//     editMenu.items = List(addItem, copyItem, pasteItem, cutItem)
//     
//     return List(fileMenu, editMenu)
//  }
  
  private def startServer(): Unit = {
    Future {
      val sskt = new ServerSocket(8088)
      while(true){
        val skt = sskt.accept()
        val ois = new ObjectInputStream(new BufferedInputStream(skt.getInputStream))
        val title = ois.readUTF()
        ois.readObject() match {
          case d: Drawing =>
            Platform.runLater(addDrawing(d, title))
          case _ => 
            println("No drawing received.")
        }
        ois.close()
        skt.close()
      }
    }
  }
  
  private def addDrawing(d: Drawing, name:String): Unit = {
    val (tab, tree) = createTabs(d, "Untitled")
    drawings = drawings :+ d -> tree
    tabPane += tab
    d.draw()
  }
  
  private def sendDrawing(): Unit = {
    val current = tabPane.selectionModel().selectedIndex()
    if(current >= 0){
      val (d, _) = drawings(current)
      val host = showInputDialog("Choose a machine send to...", "machine name")
      host.foreach { m =>  
        val skt = new Socket(m, 8088)
        val oos = new ObjectOutputStream(new BufferedOutputStream(skt.getOutputStream))
        val title = showInputDialog("title of the drawing?", "drawing title")
        title.foreach { t =>  
          oos.writeUTF(t)
          oos.writeObject(d)
          oos.close()
          skt.close()
        }
      }
    }
    
  }
  
  private def showInputDialog(msg:String, title: String): Option[String] = {
    val dialog = new TextInputDialog
    dialog.title = title
    dialog.contentText = msg
    dialog.showAndWait()
  }
}








