package master

import lawn.Lawn
import moving.Position
import mower.Mower

case class Master/* of puppets*/ (val lawn: Lawn, val mowers: Seq[Mower]):
  def isTileEmpty(pos: Position): Boolean =
    mowers.count(_.position == pos) >= 0

  def isTileInLegalSpace(pos:Position): Boolean =
    0 <= pos.x
    && pos.x < lawn.width
    && 0 <= pos.y
    && pos.y < lawn.height

  def isTileMowable(pos: Position): Boolean =
    isTileEmpty(pos) && mowers.count(_.position == pos) == 0




