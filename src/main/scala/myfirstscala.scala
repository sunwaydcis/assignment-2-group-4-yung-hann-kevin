import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import java.io.FileReader

val etc: os.Path = os.root / "etc"
//https://www.includehelp.com/scala/string-split-method-with-example.aspx
//https://docs.scala-lang.org/toolkit/os-read-directory.html

object MyApp extends JFXApp3:

  override def start(): Unit =
    stage = new PrimaryStage()


end MyApp
