import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import java.io.FileReader

val etc: os.Path = os.root / "etc"

object MyApp extends JFXApp3:

  override def start(): Unit =
    stage = new PrimaryStage()


end MyApp
