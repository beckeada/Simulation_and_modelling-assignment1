import scala.util.Random
object RandomGenerator{
  val generator = new Random
 //exponentialy distributed values - Call durations, Inter arrival times
  def getExp(lambda: Double):Double = Math.log(1-generator.nextDouble())/(-lambda)

  def getCallDur:Double = getExp(1/99.83194913549542) +10.003951603252272 +10
  /*def getCallDur:Double ={
   val k = Math.log((1-generator.nextDouble())/(-1/99.83194913549542))
   println(s"k: ${k}")
   k/99.83194913549542
  }*/
  def getInterArrival: Double = getExp(1/1.3696799447838768) + 0.0000251

  //uniformly distributed values - stations
  def getStation(range: (Int,Int)= (0,19)): Int = generator.nextInt(range._2) + range._1

  def getPosition:Double = generator.nextDouble()*2000


 //direction - if true then in possitive direction 1=>2=>3=>4
  def getDirection:Boolean = generator.nextBoolean()

  //normally distributed values - speed
  def getGauss(mean: Double , deviation: Double):Double = generator.nextGaussian() * deviation + mean

  def getSpeed: Double = getGauss(120.07209801685764,81.33527102508032)


}
