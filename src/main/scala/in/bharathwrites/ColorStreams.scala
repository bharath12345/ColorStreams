package in.bharathwrites

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.control.NonFatal

object ColorStreams {

  object Color extends Enumeration {
    type Color = Value
    val R, G, B = Value
  }

  import Color._

  type Channel = Int
  type ChannelNumber = Int

  case class Data(color: Color, channel: ChannelNumber, uniqId: Long) {
    override def toString = s"$color${channel}_$uniqId"
  }

  private val DataRegex = """([RGB])([12])_(\d+)""".r
  private val DataPattern = DataRegex.pattern

  private var channelOne: Vector[Data] = Vector()
  private var channelTwo: Vector[Data] = Vector()
  private var result: Vector[(Data, Data)] = Vector()

  def main(args: Array[String]) = getInput()

  private def printChannels() = {
    println(s"channel one = $channelOne")
    println(s"channel two = $channelTwo")
  }

  private def compute(firstChannel: Vector[Data]): Unit = {
    // printChannels()

    def matchColor(data: Data, secondChannel: Vector[Data]): Boolean = {
      secondChannel.headOption match {
        case Some(dataTwo) =>
          if (data.color == dataTwo.color) {
            result = result :+ (data, dataTwo)
            channelOne = channelOne.filter(_ != data)
            channelTwo = channelTwo.filter(_ != dataTwo)
            true
          } else {
            matchColor(data, secondChannel.tail)
          }
        case None =>
          false
      }
    }

    firstChannel.headOption match {
      case Some(dataOne) =>
        matchColor(dataOne, channelTwo) match {
          case true =>
            compute(channelOne)
          case false =>
            compute(firstChannel.tail)
        }

      case None =>
    }
  }

  @tailrec private def getInput(): Unit = {
    val channel = getChannel()
    channel match {
      case 8 =>
        compute(channelOne)
        println(s"Result: ${result}")
        getInput()
      case 9 =>
        compute(channelOne)
        println(s"Result: ${result}")
      case _ =>
        val data = getData()
        data.foreach { elem =>
          elem.channel match {
            case 1 => channelOne = channelOne :+ elem
            case 2 => channelTwo = channelTwo :+ elem
          }
        }
        getInput()
    }
  }

  private def getChannel(): Channel = {
    try {
      println(
        """Enter:
          |1 or 2 for Channel Number and hit enter
          |8 to print results and go back to feeding more data
          |9 to print results and end the program:""".stripMargin)
      val inLine = StdIn.readLine()
      val c = inLine.toInt
      if (c == 1 || c == 2 || c == 8 || c == 9) c
      else throw new Exception("Channel should be 1 or 2 (or 8 or 9)")
    } catch {
      case NonFatal(e) =>
        e.printStackTrace()
        getChannel()
    }
  }

  private def getData(): Array[Data] = {
    try {
      println("Enter data and hit enter (use space as delimiter to enter multiple data elements at once):")
      val inLine = StdIn.readLine()
      val delements = inLine.split(" +")
      delements.flatMap { delem =>
        if (DataPattern.matcher(delem).matches()) {
          val DataRegex(a, b, c) = delem
          val color = a match {
            case "R" => R
            case "G" => G
            case "B" => B
          }
          Some(Data(color, b.toInt, c.toLong))
        } else {
          println(s"Data element $delem not in proper structure")
          None
        }
      }
    } catch {
      case NonFatal(e) =>
        e.printStackTrace()
        getData()
    }
  }
}