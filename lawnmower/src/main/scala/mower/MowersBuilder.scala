package mower

import moving.Orientation.EAST
import moving.{Move, Orientation, Position}

object MowersBuilder :
  def buildMowers(linesToParse: Seq[String]): Seq[Mower]=
    //TODO pretty clumsy

    val strMowers = linesToParse.sliding(2, 2).toSeq

    strMowers.foldLeft(Seq[Mower]()){
      (acc, element) =>
        val posAndOr= element.head.split(" ")  //TODO use REGEX

        val orientation = posAndOr.last
        val position = posAndOr.filterNot(_ == orientation).map(_.toInt)

        val strMoves = element.tail.head.toList.map(_.toString)

        val mow = Mower(
          Position(position),
          Orientation(orientation),
          strMoves.map(Move(_).get) //TODO unsafe
        )
        acc :+ mow
    }
