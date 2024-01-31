package reader
import lawn.{Lawn, LawnBuilder}
import mower.Mower

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

object Reader :
  def readFromResources(fileName: String): Option[Lawn] =
    val charToDrop = (x:Char) => x == '\r'
    val lines = Using(Source.fromResource(fileName)){_.mkString.filterNot(charToDrop).split("\n")}

    lines match
      case Failure(exception) => throw Error()
      case Success(value) => LawnBuilder.buildFrom(value)









