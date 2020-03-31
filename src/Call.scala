
class Call(var station: Int, val speed: Double, var position: Double, var duration: Double, val direction: Boolean, var handover: Boolean = false) {
  def resetPosition(): Unit = position = 2000

  def updateDuration(time: Double): Unit = duration = time

  def substractDuration(time: Double): Unit = duration -= time

  def updateStation(): Int = {
    if (direction) station += 1 else station -= 1
    station
  }

  def setHandover(status: Boolean) = handover = status

  def print:String = s"(${station}, $speed, $position, $duration, $direction, $handover)"

}
