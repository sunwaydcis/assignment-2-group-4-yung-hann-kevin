import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scala.io.Source


@main def main(): Unit =
  val filename = "./src/main/resources/hospital.csv"

  // code retrieved from https://stackoverflow.com/questions/31453511/how-to-read-a-text-file-using-relative-path-in-scala
  val bufferedSource = Source.fromFile(filename)
  var data: List[List[String]] = List()

  for (line <- bufferedSource.getLines().drop(1))
    val column = line.split(",").map(_.trim).toList
    data = data :+ column

  bufferedSource.close()
  println(data)