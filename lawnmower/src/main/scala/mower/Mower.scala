package mower

import moving.{Move, Orientation, Position}

case class Mower(position: Position, orientation: Orientation, moves: List[Move]):
  def popMove :Option[Move] = this.moves.headOption

  override def toString: String = s"${position} ${orientation.toString.take(1)}"
