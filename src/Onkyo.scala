package onkyo

import java.nio.ByteBuffer
import io.Source
import java.net.{SocketTimeoutException, Socket}

class OnkyoMessage(val cmd: String, val msg: String) {
  private val CR = 0x0d byteValue
  private val LF = 0x0a byteValue

  protected def toBytes(x: Any) : Array[Byte] = {
    x match {
      case y:String => y getBytes "ASCII"
      case y:Long => ByteBuffer.allocate(8).putLong(y).array()
      case y:Int => ByteBuffer.allocate(4).putInt(y).array()
      case y:Short  => ByteBuffer.allocate(2).putShort(y).array()
      case y:Byte  => Array(y)
      case _ => throw new IllegalArgumentException()
    }
  }

  def bytes : Array[Byte] = {
    toBytes("!1" + cmd + msg)  :+ CR :+ LF
  }
}

trait OnkyoHeader extends OnkyoMessage {
  private val VERSION = 0x01 byteValue
  private val zb = Array.fill(3)(0 byteValue)

  override def bytes : Array[Byte] = {
    val msgBytes = super.bytes
    toBytes("ISCP") ++ toBytes(16) ++ toBytes(msgBytes.length) ++ toBytes(VERSION) ++ zb ++ msgBytes
  }
}

case class OnkyoConnection(host: String, port: Int) {
  private val socket = new Socket(host,port)
  socket setKeepAlive true
  socket.setSoTimeout(1000)

  val lines = Source.fromInputStream(socket.getInputStream).getLines()
  lines.next()

  def ! (cmd: String, msg: String) : Unit = {
    this ! new OnkyoMessage(cmd, msg) with OnkyoHeader
  }

  def ! (onkyoMessage: OnkyoMessage) : Unit = {
    send(onkyoMessage)
    println(response())
  }

  private def send(onkyoMessage: OnkyoMessage) {
    socket.getOutputStream write  (onkyoMessage.bytes)
  }

  private def response() : String = {
    try {
      val response = lines.next;
      if(response.startsWith("ISCP")) lines.next else response
    } catch {
      case e:SocketTimeoutException => "failed"
      case e => throw e
    }
  }
}