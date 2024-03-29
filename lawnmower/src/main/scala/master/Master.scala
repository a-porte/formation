package master

import lawn.Lawn
import moving.{Move, Orientation, Position}
import mower.Mower

import scala.annotation.tailrec
import scala.collection.immutable.HashMap
import scala.math

case class Master/* of puppets*/ (val lawn: Lawn, val mowers: Seq[Mower]):

  def isTileEmpty(pos: Position): Boolean =
    mowers.count(_.position == pos) >= 0

  @deprecated
  def isTileInLegalSpace(pos:Position): Boolean =
    0 <= pos.x
    && pos.x < lawn.width
    && 0 <= pos.y
    && pos.y < lawn.height

  def isTileMowable(pos: Position): Boolean =
    isTileEmpty(pos) && mowers.count(_.position == pos) == 0

  private val clockwise = HashMap[Orientation, Orientation](
     Orientation.NORTH -> Orientation.EAST,
     Orientation.EAST -> Orientation.SOUTH,
     Orientation.SOUTH -> Orientation.WEST,
     Orientation.WEST -> Orientation.NORTH
   )

  private val counterClockwise = clockwise.map{case (from, to) => to -> from}

  private def nextLegalPositionAndOr(initialPos: Position, orientation: Orientation): Position =
    def intWithinRange(upperBound: Int, increment: Int) = {
      math.max(0, math.min(upperBound, increment))
    }

    val theoreticalPos =
      Position(
        intWithinRange(lawn.width, initialPos.x + orientation.leftOrright),
        intWithinRange(lawn.height, initialPos.y + orientation.upOrDown)
      )
    if isTileMowable(theoreticalPos) then
      theoreticalPos
    else
      initialPos

  private def nextOrientation(or: Orientation, m: Move) : Orientation=  m match
    case moving.Move.RIGHT => clockwise(or)
    case moving.Move.LEFT => counterClockwise(or)
    case _ => throw Error()

  private def nextMowerState(toMove:Mower): Mower =
    toMove.popMove match
      case None => toMove
      case Some(move) =>
        val (newPos, newOr) = move match
            case Move.CONTINUE => (nextLegalPositionAndOr(toMove.position, toMove.orientation), toMove.orientation)
            case m @ (Move.RIGHT | Move.LEFT) => (toMove.position, nextOrientation(toMove.orientation, m))//.orElse(toMove.orientation))
        toMove.copy(newPos, newOr, toMove.moves.tail)


  def play: Master =
    @tailrec
    def iter(m: Master): Master =
      if m.mowers.exists(_.moves.nonEmpty) then
        iter(m.nextMasterState)
      else
        m
    iter(this)

  private def nextMasterState =
    this.copy(mowers = mowers.map(nextMowerState))


  override def toString: String = mowers.mkString("\n")
    




