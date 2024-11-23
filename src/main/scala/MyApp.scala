import scala.io.Source

abstract class State(val date: String, val state: String, val beds: Int, val bedsCovid: Int, val bedsNoncrit: Int, val admittedPui: Int, val admittedCovid: Int, val admittedTotal: Int, val dischargedPui: Int, val dischargedCovid: Int, val dischargedTotal: Int, val hospCovid: Int, val hospPui: Int, val hospNoncovid: Int) {
  def displayInfo(): Unit
}

class Johor(_date: String,
            _beds: Int,
            _bedsCovid: Int,
            _bedsNoncrit: Int,
            _admittedPui: Int,
            _admittedCovid: Int,
            _admittedTotal: Int,
            _dischargedPui: Int,
            _dischargedCovid: Int,
            _dischargedTotal: Int,
            _hospCovid: Int,
            _hospPui: Int,
            _hospNoncovid: Int) extends State(_date, "Johor", _beds, _bedsCovid, _bedsNoncrit, _admittedPui, _admittedCovid, _admittedTotal, _dischargedPui, _dischargedCovid, _dischargedTotal, _hospCovid, _hospPui, _hospNoncovid) {
  override def displayInfo(): Unit = {
    println(s"Date: $date, State: Johor, Beds: $beds, Beds Covid: $bedsCovid, Beds Non-Critical: $bedsNoncrit, Admitted PUI: $admittedPui, Admitted Covid: $admittedCovid, Admitted Total: $admittedTotal, Discharged PUI: $dischargedPui, Discharged Covid: $dischargedCovid, Discharged Total: $dischargedTotal, Hospitalized Covid: $hospCovid, Hospitalized PUI: $hospPui, Hospitalized Non-Covid: $hospNoncovid")
  }
}

class Selangor(_date: String,
            _beds: Int,
            _bedsCovid: Int,
            _bedsNoncrit: Int,
            _admittedPui: Int,
            _admittedCovid: Int,
            _admittedTotal: Int,
            _dischargedPui: Int,
            _dischargedCovid: Int,
            _dischargedTotal: Int,
            _hospCovid: Int,
            _hospPui: Int,
            _hospNoncovid: Int) extends State(_date, "Selangor", _beds, _bedsCovid, _bedsNoncrit, _admittedPui, _admittedCovid, _admittedTotal, _dischargedPui, _dischargedCovid, _dischargedTotal, _hospCovid, _hospPui, _hospNoncovid) {
  override def displayInfo(): Unit = {
    println(s"Date: $date, State: Selangor, Beds: $beds, Beds Covid: $bedsCovid, Beds Non-Critical: $bedsNoncrit, Admitted PUI: $admittedPui, Admitted Covid: $admittedCovid, Admitted Total: $admittedTotal, Discharged PUI: $dischargedPui, Discharged Covid: $dischargedCovid, Discharged Total: $dischargedTotal, Hospitalized Covid: $hospCovid, Hospitalized PUI: $hospPui, Hospitalized Non-Covid: $hospNoncovid")
  }
}

@main def main(): Unit = {
  val filename = "hospital.csv"

  val source = Source.fromResource(filename)
  val lines = source.getLines().drop(1) // Drop the header line

  val states = lines.collect {
    case line if line.contains("Johor") =>
      val Array(date, state, beds, bedsCovid, bedsNoncrit, admittedPui, admittedCovid, admittedTotal, dischargedPui, dischargedCovid, dischargedTotal, hospCovid, hospPui, hospNoncovid) = line.split(",")
      new Johor(date, beds.toInt, bedsCovid.toInt, bedsNoncrit.toInt, admittedPui.toInt, admittedCovid.toInt, admittedTotal.toInt, dischargedPui.toInt, dischargedCovid.toInt, dischargedTotal.toInt, hospCovid.toInt, hospPui.toInt, hospNoncovid.toInt)
    case line if line.contains("Selangor") =>
      val Array(date, state, beds, bedsCovid, bedsNoncrit, admittedPui, admittedCovid, admittedTotal, dischargedPui, dischargedCovid, dischargedTotal, hospCovid, hospPui, hospNoncovid) = line.split(",")
      new Selangor(date, beds.toInt, bedsCovid.toInt, bedsNoncrit.toInt, admittedPui.toInt, admittedCovid.toInt, admittedTotal.toInt, dischargedPui.toInt, dischargedCovid.toInt, dischargedTotal.toInt, hospCovid.toInt, hospPui.toInt, hospNoncovid.toInt)
  }.toList

  val stateWithMostBeds = states.maxBy(_.beds)
  stateWithMostBeds.displayInfo()
}