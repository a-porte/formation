package reader

import org.scalatest.funspec.AnyFunSpec
import lawn.Lawn
import moving.{Move, Orientation, Position}
import mower.Mower

import scala.util.Try

class ReaderTest extends AnyFunSpec:
  //TODO : using mocks instead of real objects?
  val fileName = "input.txt"
  val north = Orientation.NORTH
  val moves =
    Seq(Move.CONTINUE :: Move.RIGHT :: Move.CONTINUE :: Move.RIGHT :: Move.CONTINUE :: Move.CONTINUE :: Nil)
      ++ Seq(Move.CONTINUE :: Move.LEFT :: Move.CONTINUE :: Nil)
  val mowers =
    Mower(Position(1, 2), north, moves.head)
      :: Mower(Position(1, 1), north, moves.tail.head) :: Nil

  val lawn = Lawn(2, 3, mowers)

  describe("When the file is read") {
    it("should return a lawn") {
      assert(Reader.readFromResources(fileName).contains(lawn)) // instead of Reader. ... == Some(lawn) !!
    }

  }

