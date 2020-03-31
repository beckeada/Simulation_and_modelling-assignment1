import scala.collection.mutable
import scala.collection.mutable.PriorityQueue

object main extends App{
    var sum_Stats:(Double,Double) = (0,0)
    def sum (a:(Double,Double),b:(Double,Double))= (a._1+b._1,a._2+b._2)

    for(i <- 0 to 99){
        val world1 = World()
        world1.start()
        sum_Stats= (sum(sum_Stats,world1.getStatistics))
    }
    println("World1: ")

    //world1.statistics()


    println(s"Percentage of blocked ${sum_Stats._1}%.")
    println(s"Percentage of dropped ${sum_Stats._2}%.")


    sum_Stats = (0,0)

    println("\nWorld2:")
    for(i <- 0 to 99){
        val world2 = World(1)
        world2.start()
        sum_Stats= (sum(sum_Stats,world2.getStatistics))
    }

    println(s"Percentage of blocked ${sum_Stats._1}%.")
    println(s"Percentage of dropped ${sum_Stats._2}%.")
    /*println("World1: ")
    val world1 = World()
    world1.start()
    world1.statistics()

    println("\nWorld2:")
    val world2 = World(1)
    world2.start()
    world2.statistics()
*/

   /* println(RandomGenerator.getInterArrival)
    println(RandomGenerator.getCallDur)*/
}


