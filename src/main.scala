
object main extends App {

  def sum(a: (Double, Double), b: (Double, Double)): (Double, Double) = (a._1 + b._1, a._2 + b._2)

  def multipleRuns(reserved: Int, repetitions: Int = 99): (Double, Double) = {

    println(s"World$reserved: ")
    var sum_Stats: (Double, Double) = (0, 0)

    for (i <- 0 to repetitions) {
      val world = World(reserved)
      world.start()
      sum_Stats = sum(sum_Stats, world.getStatistics)
    }
    (sum_Stats._1 / repetitions, sum_Stats._2 / repetitions)
  }


  println("World1: ")
  val world1 = World()
  world1.start()
  world1.statistics()

  println("\nWorld2:")
  val world2 = World(1)
  world2.start()
  world2.statistics()

}