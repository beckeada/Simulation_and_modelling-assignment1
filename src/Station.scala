case class Station(max_normal: Int = 10, max_handover: Int = 0) {
  var taken_normal = 0
  var taken_handover = 0

  override def toString = s"station $taken_normal,$taken_handover "


  def print: String = s"[$taken_normal, $taken_handover]"

  def canStart(handover: Boolean = false): Boolean = {
    if ((max_normal - taken_normal > 0) || (handover && (max_handover - taken_handover > 0)))
      return true
    false
  }

  //true - handover_channel
  def startCall(handover: Boolean = false): Boolean = {
    if (max_normal - taken_normal > 0) {
      taken_normal += 1
      return false
    }
    else if (handover && (max_handover - taken_handover > 0)) {
      taken_handover += 1
      return true
    }
    throw new RuntimeException
  }

  def free(usedHandover: Boolean): Unit = {
    if (usedHandover) taken_handover -= 1
    else
      taken_normal -= 1
  }


}
