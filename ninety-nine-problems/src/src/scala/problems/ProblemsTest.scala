package problems

import org.scalatest.funspec.AnyFunSpec


class ProblemsTest extends AnyFunSpec :
  describe("when PO receives a list") {
    it("should return the last element") {
      assert(Problems.last(1 :: 2 :: 3 :: Nil) == 3)
    }
    it("should return the last element for a recursion") {
      assert(Problems.last(1 :: 2 :: 3 :: Nil, isRec = true) == 3)
    }
  }
  describe("when we want the penultimate element ") {
    it("should be returned") {
      assert(Problems.penultimate(1 :: 2 :: 3 :: Nil) == 2)
    }
    it("should be returned even with a recursion") {
      assert(Problems.penultimate(1 :: 2 :: 3 :: Nil, isRec = true) == 2)
    }
  }

  describe("when we want the nth element ") {
    it("should be returned") {
      assert(Problems.nth(1, 1 :: 2 :: 3 :: Nil) == 1)
    }
    it("should be returned even with a recursion") {
      assert(Problems.nth(1, 1 :: 2 :: 3 :: Nil, isRec = true) == 1)
    }
  }


