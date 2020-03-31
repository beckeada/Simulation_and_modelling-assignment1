
import scala.collection.mutable

case class World(r_st: Int = 0) {
  val stations: Array[Station] = Array.fill(20)(Station(10 - r_st, r_st))
  var total_calls = 0
  var total_blocked = 0
  var total_dropped = 0
  var total_okcalls = 0
  var time: Double = 0
  var warmUp: Boolean = true

  var fel: mutable.PriorityQueue[Event] = mutable.PriorityQueue[Event]()(timeOrdering)

  def addEvent(event: Event): Unit = fel.enqueue(event)

  def statistics(): Unit = {
    println(s"Total calls: $total_calls")
    println(s"Total dropped: $total_dropped")
    println(s"Total blocked: $total_blocked")
    println(s"Total okcalls: $total_okcalls")
    println(s"Percentage of blocked ${(total_blocked.toDouble / total_calls) * 100}%.")
    println(s"Percentage of dropped ${(total_dropped.toDouble / total_calls) * 100}%.")
    println(s"Global time: $time")
  }

  def getStatistics: (Double, Double) = ((total_blocked.toDouble / total_calls) * 100, (total_dropped.toDouble / total_calls) * 100)

  def resetStats(): Unit = {
    total_calls = 0
    total_blocked = 0
    total_dropped = 0
    total_okcalls = 0
    warmUp = false
  }

  def plan_call(): Unit = {
    if (warmUp && total_calls == 10000) resetStats()

    val t: Double = time + RandomGenerator.getInterArrival
    fel.enqueue(Start(t))
  }

  def finished: Boolean = fel.isEmpty || total_calls >= 100000

  def start(): Unit = {
    fel.enqueue(Start(0))
    while (!finished) {
      val event = fel.dequeue()
      time = event.getTime
      event.compute(this)
    }
  }

  def print(): Unit = {
    println("Stations:")
    for (st <- 0 until 20) {
      println(s"$st + ${stations(st).print}")
    }
  }


}
