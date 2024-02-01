package mower

import moving.Orientation.WEST
import moving.Position
import org.scalatest.funspec.AnyFunSpec

class MowerTest extends AnyFunSpec{
  describe("When priting a Mower") {
    it("should print correctly") {
      val m = Mower(Position(1,2), WEST, Nil)
      assert(m.toString == "1 2 W")
    }
  }
}
