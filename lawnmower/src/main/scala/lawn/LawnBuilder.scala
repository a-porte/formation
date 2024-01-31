package lawn

import mower.MowersBuilder

object LawnBuilder:
  def buildFrom(lines:Seq[String]): Option[Lawn]=
    if lines.nonEmpty then // instead of patmat on size > 0
      val widthAndHeight = lines.head.split(" ").toSeq
      Some(
        Lawn(
          widthAndHeight.head.toInt, //TODO use regex
          widthAndHeight.reverse.head.toInt,
          MowersBuilder.buildMowers(lines.tail)
        )
      )
    else
      None





