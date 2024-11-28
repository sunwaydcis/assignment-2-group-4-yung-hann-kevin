import scala.io.Source

// Represents a state
case class States(val date: String, val state: String, val beds: Int, val bedsCovid: Int, val bedsNoncrit: Int, val admittedPui: Int, val admittedCovid: Int, val admittedTotal: Int, val dischargedPui: Int, val dischargedCovid: Int, val dischargedTotal: Int, val hospCovid: Int, val hospPui: Int, val hospNoncovid: Int) {
  // Method to display the state information
  def displayInfo(): Unit = {
    println(s"Date: $date, State: $state, Beds: $beds, Beds Covid: $bedsCovid, Beds Non-Critical: $bedsNoncrit, Admitted PUI: $admittedPui, Admitted Covid: $admittedCovid, Admitted Total: $admittedTotal, Discharged PUI: $dischargedPui, Discharged Covid: $dischargedCovid, Discharged Total: $dischargedTotal, Hospitalized Covid: $hospCovid, Hospitalized PUI: $hospPui, Hospitalized Non-Covid: $hospNoncovid")
  }

  // Loads the data from the csv file into a list
  def loadStates(filename: String): List[States] = {
    val source = Source.fromResource(filename)
    val lines = source.getLines().drop(1) // Drop the header line

    lines.map(line => {
      val Array(date, state, beds, bedsCovid, bedsNoncrit, admittedPui, admittedCovid, admittedTotal, dischargedPui, dischargedCovid, dischargedTotal, hospCovid, hospPui, hospNoncovid) = line.split(",")
      States(date, state, beds.toInt, bedsCovid.toInt, bedsNoncrit.toInt, admittedPui.toInt, admittedCovid.toInt, admittedTotal.toInt, dischargedPui.toInt, dischargedCovid.toInt, dischargedTotal.toInt, hospCovid.toInt, hospPui.toInt, hospNoncovid.toInt)
    }).toList
  }
}

// Displays which state has the most amount of beds
class StateWithMostBeds(val _date: String,
                        val _state: String,
                        val _beds: Int) extends States(_date, _state, _beds, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) {
  def displayMaxBed(): Unit = {
    println(s"State with most beds is $_state with $_beds beds on the date $_date")
  }
}

// Calculates and displays the ratio of beds for COVID-19 patients to total beds
class CovidBedRatio(val _beds: Int,
                    val _bedsCovid: Int) extends States(null, null, _beds, _bedsCovid, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) {
  def displayCovidBedRatio(): Unit = {
    val ratioCovidBed = (_bedsCovid.toDouble / _beds)
    println(s"The ratio of beds dedicated for COVID-19 patients to the total amount of available hospital beds is $_bedsCovid:$_beds or $ratioCovidBed")
  }
}

// Calculates the average amount of patients admitted everyday to hospitals throughout a state for either COVID-19 or non COVID-19 reasons
class AverageStatePatients (val states:List[States]) {
  def calculateAverageAdmittedPatientsByState(states: List[States]): Unit = {
    val statesGrouped = states.groupBy(_.state)
    statesGrouped.foreach { case (stateKey, stateList) =>
      val numStates = stateList.length
      val covidAdmitted = stateList.map(_.admittedCovid).sum
      val avgCovidAdmitted = (covidAdmitted.toDouble / numStates).round
      val nonCovidAdmitted = stateList.map(_.admittedPui).sum
      val avgNonCovidAdmitted = (nonCovidAdmitted.toDouble / numStates).round
      println(s"Average admitted patients per day for COVID-19 in $stateKey: $avgCovidAdmitted")
      println(s"Average admitted patients per day not for COVID-19 in $stateKey: $avgNonCovidAdmitted")
      println(s"=========================================================================================")
    }
  }
}

@main def main(): Unit = {
  val filename = "hospital.csv"

  // Load the states from the CSV file
  val states = new States("", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).loadStates(filename)

  // Find the state with the most beds
  val stateWithMostBedsObj = new StateWithMostBeds(states.maxBy(_.beds).date, states.maxBy(_.beds).state, states.maxBy(_.beds).beds)
  stateWithMostBedsObj.displayMaxBed()
  println(s"\n=========================================================================================\n")

  // Create an instance of CovidBedRatio
  val covidBedRatioObj = new CovidBedRatio(states.map(_.beds).sum, states.map(_.bedsCovid).sum)
  // Call the displayCovidBedRatio method
  covidBedRatioObj.displayCovidBedRatio()
  println(s"\n=========================================================================================")

  // Create an instance of AverageStatePatients
  val averageStatePatientsObj = new AverageStatePatients(states)
  // Call the calculateAverageAdmittedPatientsByState method
  averageStatePatientsObj.calculateAverageAdmittedPatientsByState(states)
}