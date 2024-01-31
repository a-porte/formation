package reader
import lawn.{Lawn, LawnBuilder}
import mower.{Mower, MowersBuilder}

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

object Reader :
  def readFromResources(fileName: String): Option[(Lawn, Seq[Mower])] =
    val charToDrop = (x:Char) => x == '\r'
    val lines = Using(Source.fromResource(fileName)){_.mkString.filterNot(charToDrop).split("\n")}

    lines match
      case Failure(exception) => throw Error(exception)
      case Success(value) =>
        if value.nonEmpty then
          val widthAndHeight = value.head.split(" ").toSeq
          Some(
            LawnBuilder.buildFrom(
              widthAndHeight.head.toInt,
              widthAndHeight.reverse.head.toInt
            ), 
            MowersBuilder.buildMowers(value.tail)
          )
        else
          None  









