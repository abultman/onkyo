package onkyo

object Remote {

  def main(args: Array[String]) = {
    val connection: OnkyoConnection = OnkyoConnection("192.168.0.198", 60128)
    connection ! ("NSL", "QSTN")
    connection ! ("AMT", "QSTN")
    connection ! ("MVL", "QSTN")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "UP")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
    connection ! ("MVL", "DOWN")
  }
}