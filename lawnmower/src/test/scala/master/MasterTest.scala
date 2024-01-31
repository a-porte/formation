package master

import lawn.Lawn
import moving.Orientation.NORTH
import moving.{Move, Position}
import mower.Mower
import org.scalatest.funspec.AnyFunSpec

class MasterTest extends AnyFunSpec {
  val lawn = Lawn(4,5)
  val outOfBoundPosition = Position(-1,0) // tile A
  val emptyTile = Position(1,0) //tile B below
  /*
  |   |   |   |   |
  |   |   |   | D |
  |   |   |   | C |
  |   |   |   |   |
 A|   | B |   |   |

   */


  val mowers = Seq(
    Mower(Position(3,2), NORTH, List(Move.CONTINUE)), // on tile C above
    Mower(Position(3,3), NORTH, List(Move.CONTINUE)) // on tile D above
  )

  val mowersPrime = Seq(
    Mower(Position(3,2), NORTH, List()),
    Mower(Position(3,4), NORTH, List())
  )
  /*
    after playing, the intented positions are respectively (3,2) (because the 1st mower is blocked by the 2nd) and (3,4)
   */
  val master = Master(lawn, mowers)
  describe("Master, when dealing with") {
    describe("an empty tile") {
      it("should return true") {
        assert(master.isTileEmpty(emptyTile))
      }
    }
    describe("an outside bound tile") {
      it("should return false") {
        assert(! master.isTileInLegalSpace(outOfBoundPosition))
      }
    }
    describe("a tile with a tile where a mower already is ") {
      it("should return false in order to prevent collisions") {
        assert(!master.isTileMowable(mowers.head.position))
      }
    }
    describe("an mower"){
      describe("that can me moved") {
        it("should move it") {
          val mPrime = master.play
          assert(mPrime.mowers.tail.head.position == Position(3, 4))
        }
      }
      describe("that can't be moved") {
        it("should not move it") {
          val mPrime = master.play
          assert(mPrime.mowers.head.position == Position(3, 2))
        }
      }
    }
    describe("several mowers") {
      it("should only move them sequentially") {
        val mPrime = master.copy(mowers = mowersPrime)
        assert(master.play == mPrime)
      }
    }
  }

}
