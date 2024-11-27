import scala.io.Source

// Represents a state
abstract class States(val date: String, val state: String, val beds: Int, val bedsCovid: Int, val bedsNoncrit: Int, val admittedPui: Int, val admittedCovid: Int, val admittedTotal: Int, val dischargedPui: Int, val dischargedCovid: Int, val dischargedTotal: Int, val hospCovid: Int, val hospPui: Int, val hospNoncovid: Int) {
  // Method to display the state information
  def displayInfo(): Unit = {}
}

class DefaultStates(val _date: String,
                    val _state: String,
                    val _beds: Int,
                    val _bedsCovid: Int,
                    val _bedsNoncrit: Int,
                    val _admittedPui: Int,
                    val _admittedCovid: Int,
                    val _admittedTotal: Int,
                    val _dischargedPui: Int,
                    val _dischargedCovid: Int,
                    val _dischargedTotal: Int,
                    val _hospCovid: Int,
                    val _hospPui: Int,
                    val _hospNoncovid: Int) extends States(_date, _state, _beds, _bedsCovid, _bedsNoncrit, _admittedPui, _admittedCovid, _admittedTotal, _dischargedPui, _dischargedCovid, _dischargedTotal, _hospCovid, _hospPui, _hospNoncovid) {
  override def displayInfo(): Unit = {
    println(s"Date: $_date, State: $_state, Beds: $_beds, Beds Covid: $_bedsCovid, Beds Non-Critical: $_bedsNoncrit, Admitted PUI: $_admittedPui, Admitted Covid: $_admittedCovid, Admitted Total: $_admittedTotal, Discharged PUI: $_dischargedPui, Discharged Covid: $_dischargedCovid, Discharged Total: $_dischargedTotal, Hospitalized Covid: $_hospCovid, Hospitalized PUI: $_hospPui, Hospitalized Non-Covid: $_hospNoncovid")
  }

  def loadStates(filename: String): List[DefaultStates] = {
    val source = Source.fromResource(filename)
    val lines = source.getLines().drop(1) // Drop the header line

    lines.map { line =>
      val Array(date, state, beds, bedsCovid, bedsNoncrit, admittedPui, admittedCovid, admittedTotal, dischargedPui, dischargedCovid, dischargedTotal, hospCovid, hospPui, hospNoncovid) = line.split(",")
      new DefaultStates(date, state, beds.toInt, bedsCovid.toInt, bedsNoncrit.toInt, admittedPui.toInt, admittedCovid.toInt, admittedTotal.toInt, dischargedPui.toInt, dischargedCovid.toInt, dischargedTotal.toInt, hospCovid.toInt, hospPui.toInt, hospNoncovid.toInt)
    }.toList
  }

  def calculateAverageAdmittedPatientsByState(states: List[DefaultStates]): Unit = {
    val statesGrouped = states.groupBy(_.state)
    statesGrouped.foreach { case (stateKey, stateList) =>
      val numStates = stateList.length
      val covidAdmitted = stateList.map(_.admittedCovid).sum
      val avgCovidAdmitted = (covidAdmitted.toDouble / numStates).round
      val nonCovidAdmitted = stateList.map(_.admittedPui).sum
      val avgNonCovidAdmitted = (nonCovidAdmitted.toDouble / numStates).round
      println(s"Average admitted patients for COVID-19 in $stateKey: $avgCovidAdmitted")
      println(s"Average admitted patients not for COVID-19 in $stateKey: $avgNonCovidAdmitted")
      println(s"=========================================================================================")
    }
  }
}


class StateWithMostBeds(val _date: String,
                        val _state: String,
                        val _beds: Int) extends States(_date, _state, _beds, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) {
  def displayMaxBed(): Unit = {
    println(s"State with most beds is $_state with $_beds beds on the date $_date")
  }
}


class CovidBedRatio(val _beds: Int,
                    val _bedsCovid: Int) extends States(null, null, _beds, _bedsCovid, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) {
  def displayCovidBedRatio(): Unit = {
    val ratioCovidBed = (_bedsCovid.toDouble / _beds)
    println(s"The ratio of beds dedicated for COVID-19 patients to the total amount of available hospital beds is $_bedsCovid:$_beds or $ratioCovidBed")
  }
}

@main def main(): Unit = {
  val filename = "hospital.csv"

  // Load the states from the CSV file
  val states = new DefaultStates("", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).loadStates(filename)

  // Find the state with the most beds
  val stateWithMostBedsObj = new StateWithMostBeds(states.maxBy(_.beds)._date, states.maxBy(_.beds).state, states.maxBy(_.beds)._beds)
  stateWithMostBedsObj.displayMaxBed()
  println(s"\n=========================================================================================\n")

  // Create an instance of CovidBedRatio
  val covidBedRatioObj = new CovidBedRatio(states.map(_.beds).sum, states.map(_.bedsCovid).sum)
  // Call the displayCovidBedRatio method
  covidBedRatioObj.displayCovidBedRatio()
  println(s"\n=========================================================================================")

  // Create an instance of DefaultStates to call filter Johor
  val defaultStatesObj = new DefaultStates("", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  // Calculate and print the average number of admitted patients by state
  defaultStatesObj.calculateAverageAdmittedPatientsByState(states)
}