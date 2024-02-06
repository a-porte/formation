package reader
import lawn.{Lawn, LawnBuilder}
import mower.{Mower, MowersBuilder}

import scala.io.Source
import scala.util.{Failure, Success, Try, Using}

object Reader :
  def readFromResources(fileName: String): Option[(Lawn, Seq[Mower])] =
    val lawnPattern = """(\d)\s+(\d)""".r
    val charToDrop = (x:Char) => x == '\r'
    val mownerPattern = """(\d)\s(\d)\s(\w)\n(\w+).*""".r

    val l = Using(Source.fromResource(fileName)){_.mkString.filterNot(charToDrop) }

    val (optLawn, optSeq) = l match
      case Failure(exception) => ??? //TODO
      case Success(value) => value.split("\n", 2).foldLeft[(Option[Lawn], Seq[Mower])](None, Nil){
        case ((None, _), lawnFurthestTile) => // 1st element should be the upper right tile
          lawnFurthestTile match
            case lawnPattern(w, h) =>(LawnBuilder.buildFrom(w, h, _+1), Nil)
            case _ => (None, Nil)

        case ((Some(lawn), mowers), element) =>
         element match
           //case mownerPattern(x, y, _*) => (None, Nil)//(Some(lawn), MowersBuilder.buildMowers(Seq(...)))  // mownerPattern does not allow this kind of construct
           case _ => element.split("\n"/*,3*//*dealing with limit requires to changes buildMowers signature*/).toList match
             case posAndOr :: moves :: tail =>  (Some(lawn), MowersBuilder.buildMowers(Seq(posAndOr, moves) ++ tail))
             case List(_, _*) => ???//TODO
             case Nil => ???//TODO
        }

    optLawn match
      case Some(value) => Some(value, optSeq)
      case None => None

