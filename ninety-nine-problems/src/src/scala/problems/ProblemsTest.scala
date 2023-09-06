package problems

import org.scalatest.funspec.AnyFunSpec


class ProblemsTest extends AnyFunSpec :
  val listToTest: List[Int] = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil map (i => i *10)
  describe("when PO receives a list") {
    it("should return the last element") {
      assert(Problems.last(listToTest) == 60)
    }
    it("should return the last element for a recursion") {
      assert(Problems.last(listToTest, isRec = true) == 60)
    }
  }
  describe("when we want the penultimate element ") {
    it("should be returned") {
      assert(Problems.penultimate(listToTest) == 50)
    }
    it("should be returned even with a recursion") {
      assert(Problems.penultimate(listToTest, isRec = true) == 50)
    }
  }

  describe("when we want to know if a list is a palindrome ") {
    it("should say so") {
      assert(Problems.isPalindrome("kayak".toList))
    }
    it("should say return false if it is not the case even with a recursion") {
      assert(!Problems.isPalindrome("kayak!".toList, isRec = true))
    }
    it("should say return true if it is the case even with a recursion") {
      assert(Problems.isPalindrome("kayak".toList, isRec = true))
    }
  }


