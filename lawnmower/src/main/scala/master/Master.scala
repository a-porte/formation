package master

import lawn.Lawn
import moving.{Move, Orientation, Position}
import mower.Mower

import scala.collection.immutable.HashMap

case class Master/* of puppets*/ (val lawn: Lawn, val mowers: Seq[Mower]):

  def isTileEmpty(pos: Position): Boolean =
    mowers.count(_.position == pos) >= 0

  def isTileInLegalSpace(pos:Position): Boolean =
    0 <= pos.x
    && pos.x < lawn.width
    && 0 <= pos.y
    && pos.y < lawn.height

  def isTileMowable(pos: Position): Boolean =
    isTileEmpty(pos) && mowers.count(_.position == pos) == 0 && isTileInLegalSpace(pos)

  private val clockwise = HashMap[Orientation, Orientation](
     Orientation.NORTH -> Orientation.EAST,
     Orientation.EAST -> Orientation.SOUTH,
     Orientation.SOUTH -> Orientation.WEST,
     Orientation.WEST -> Orientation.NORTH
   )

  private val counterClockwise = clockwise.map{case (from, to) => to -> from}

  private def nextLegalPositionAndOr(initialPos: Position, orientation: Orientation): Position =
    val theoreticalPos = Position(initialPos.x + orientation.leftOrright, initialPos.y + orientation.upOrDown)
     if isTileMowable(theoreticalPos) then
      theoreticalPos
    else
      initialPos
  private def nextMowerState(toMove:Mower): Mower =
    toMove.popMove match
      case None => toMove
      case Some(move) =>
        val (newPos, newOr) = move match
            case Move.LEFT => (toMove.position, counterClockwise(toMove.orientation))//.orElse(toMove.orientation))
            case Move.CONTINUE => (nextLegalPositionAndOr(toMove.position, toMove.orientation), toMove.orientation)
            case Move.RIGHT => (toMove.position, clockwise(toMove.orientation))//.orElse(toMove.orientation))
        toMove.copy(newPos, newOr, toMove.moves.tail)


  def play: Master =
    this.copy(mowers = mowers.map(nextMowerState))
    




