import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scala.io.Source

object MyApp extends JFXApp3:

  val filename = "hospital.csv"

  // code retrieved from https://stackoverflow.com/questions/31453511/how-to-read-a-text-file-using-relative-path-in-scala
  for (line <- Source.fromResource(filename).getLines) {
    println(line)
  }

  override def start(): Unit =
    stage = new PrimaryStage()

end MyApp
