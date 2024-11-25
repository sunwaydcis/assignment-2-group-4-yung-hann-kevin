import scala.io.Source

// Represents a state
class State(val date: String, val state: String, val beds: Int, val bedsCovid: Int, val bedsNoncrit: Int, val admittedPui: Int, val admittedCovid: Int, val admittedTotal: Int, val dischargedPui: Int, val dischargedCovid: Int, val dischargedTotal: Int, val hospCovid: Int, val hospPui: Int, val hospNoncovid: Int) {
  // Method to display the state information
  def displayInfo(): Unit = {
    println(s"Date: $date, State: $state, Beds: $beds, Beds Covid: $bedsCovid, Beds Non-Critical: $bedsNoncrit, Admitted PUI: $admittedPui, Admitted Covid: $admittedCovid, Admitted Total: $admittedTotal, Discharged PUI: $dischargedPui, Discharged Covid: $dischargedCovid, Discharged Total: $dischargedTotal, Hospitalized Covid: $hospCovid, Hospitalized PUI: $hospPui, Hospitalized Non-Covid: $hospNoncovid")
  }
}

@main def main(): Unit = {
  val filename = "hospital.csv"

  // Load the CSV file
  val source = Source.fromResource(filename)
  val lines = source.getLines().drop(1) // Drop the header line

  // Parse the CSV data into State objects
  val states = lines.map { line =>
    val Array(date, state, beds, bedsCovid, bedsNoncrit, admittedPui, admittedCovid, admittedTotal, dischargedPui, dischargedCovid, dischargedTotal, hospCovid, hospPui, hospNoncovid) = line.split(",")
    new State(date, state, beds.toInt, bedsCovid.toInt, bedsNoncrit.toInt, admittedPui.toInt, admittedCovid.toInt, admittedTotal.toInt, dischargedPui.toInt, dischargedCovid.toInt, dischargedTotal.toInt, hospCovid.toInt, hospPui.toInt, hospNoncovid.toInt)
  }.toList


  // Find the state with the most beds
  val stateWithMostBeds = states.maxBy(_.beds)
  stateWithMostBeds.displayInfo()


  // Calculate the total number of beds
  val totalBeds = states.map(_.beds).sum
  // Calculate the total number of Covid beds
  val totalBedsCovid = states.map(_.bedsCovid).sum
  // Calculate the ratio of Covid beds to total beds
  val ratioCovidBed = (totalBedsCovid.toDouble / totalBeds)
  println(s"Ratio Covid Bed: $ratioCovidBed")

  
  // Calculate the average number of admitted patients by state
  val statesGrouped = states.groupBy(_.state)
  statesGrouped.foreach { case (stateKey, stateList) =>
    val numStates = stateList.length
    val totalAdmitted = stateList.map(_.admittedTotal).sum
    val avgAdmitted = totalAdmitted.toDouble / numStates
    println(s"Average admitted patients in $stateKey: $avgAdmitted")
  }
}