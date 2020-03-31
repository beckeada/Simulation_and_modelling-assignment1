
abstract class Event(time: Double) {
  def compute(world: World)

  def getTime: Double = time

  def print():Unit
}

//timeOrdering is for sole purpose of Applying PriorityQueue on Events
//orders from lowest time to highest
object timeOrdering extends Ordering[Event] {
  def ~=(x: Double, y: Double, precision: Double) = {
    if ((x - y).abs < precision) true else false
  }

  override def compare(x: Event, y: Event): Int = {
    val difference: Double = x.getTime - y.getTime
    if (~=(x.getTime, y.getTime, 0.000000001)) {
      return 0
    }
    else if (difference > 0) {
      return -1
    }
    return 1
  }
}


case class Start(time: Double) extends Event(time) {
  def print():Unit = println(s"Start: ${time}")

  def compute(world: World): Unit = {
    //check if possible to initiate
    val station: Int = RandomGenerator.getStation()
    if (world.stations(station).canStart()) {
      world.stations(station).startCall()
      val call = new Call(station,
        RandomGenerator.getSpeed,
        RandomGenerator.getPosition,
        RandomGenerator.getCallDur,
        RandomGenerator.getDirection)


      val zoneLeaveTime = call.position / (call.speed / 3.6)

      //termination part
      if (zoneLeaveTime > call.duration) {
        world.addEvent(Termination(call.duration + world.time, call))
      }
      else if ((call.direction && station == 19) ||
        (!call.direction && station == 0)) {
        world.addEvent(Termination(zoneLeaveTime + world.time, call))
      }
      else {
        call.resetPosition
        call.substractDuration(zoneLeaveTime)
        world.addEvent(Handover(zoneLeaveTime + world.time, call))
      }
    }
    else {
      world.total_blocked += 1
      world.total_calls +=1
    }
    world.plan_call()
  }
}


case class Termination(time: Double, call: Call) extends Event(time) {
  def print():Unit = println(s"Termination ${time} - ${call.print}")

  def compute(world: World): Unit = {
    world.stations(call.station).free(call.handover)
    world.total_okcalls+=1
    world.total_calls +=1
  }
}


case class Handover(time: Double, call: Call) extends Event(time) {
  def print():Unit = println(s"Termination $time - ${call.print}")

  def compute(world: World): Unit = {
    world.stations(call.station).free(call.handover)

    val station: Int = call.updateStation()
    if (world.stations(station).canStart(true)) {
        call.setHandover(world.stations(station).startCall(true))
      val zoneLeaveTime = call.position / (call.speed / 3.6)
      //termination part
      if (zoneLeaveTime > call.duration) {
        world.addEvent(Termination(call.duration + world.time, call))
      }
      else if ((call.direction && station == 19) ||
        (!call.direction && station == 0)) {
        world.addEvent(Termination(zoneLeaveTime + world.time, call))
      }
      else {
        call.resetPosition
        call.substractDuration(zoneLeaveTime)
        world.addEvent(Handover(zoneLeaveTime + world.time, call))
      }
    }
    else {
      world.total_dropped += 1
      world.total_calls +=1
    }
  }
}
