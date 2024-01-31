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
  |   |   | D |   |
  |   |   |   |   |
  |   |   |   | C |
  |   |   |   |   |
 A|   | B |   |   |

   */


  val mowers = Seq(
    Mower(Position(3,2), NORTH, List(Move.CONTINUE)), // on tile C above
    Mower(Position(2,4), NORTH, List(Move.CONTINUE)) // on tile D above
  )
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
  }

}
