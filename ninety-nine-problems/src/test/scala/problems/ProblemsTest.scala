package problems

import org.scalatest.funspec.AnyFunSpec


class ProblemsTest extends AnyFunSpec :
  private val listToTest: List[Int] = 1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil map (i => i *10)
  private val stringList: List[String] = List("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e")
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


  describe("when we want to flatten a list") {
    it("should be ok") {
      assert(Problems.flatten(List(List(1, 1), 2, List(3, List(5, 8)))) == List(1, 1, 2, 3, 5, 8))
    }
  }

  describe("when we want to delete consecutive duplicates in a list") {
    it("should be ok") {
      assert(Problems.compress(stringList) == List("a", "b", "c", "a", "d", "e"))
    }
  }

  describe("when we want to unflatten consecutive duplicates in a list inside nested lists") {
    it("should be ok") {
      assert(Problems.pack(stringList) == List(List("a", "a", "a", "a"), List("b"), List("c", "c"), List("a", "a"), List("d"), List("e", "e", "e", "e")))
    }
  }

  describe("when we want to use precedent method to count duplicate numbers per sublist ") {
    it("should be ok") {
      assert(Problems.encode(stringList) == List((4, "a"), (1, "b"), (2, "c"), (2, "a"), (1, "d"), (4, "e")))
    }
  }

  describe("when we want to use precedent method to count duplicate numbers per sublist except singletons") {
    it("should be ok") {
      assert(Problems.encodeModified(stringList) == List((4, "a"), "b", (2, "c"), (2, "a"), "d", (4, "e")))
    }
  }

  describe("when we want to undo completly the previous encoding") {
    it("should be ok") {
      assert(Problems.decode(List((4, "a"), (1, "b"), (2, "c"), (2, "a"), (1, "d"), (4, "e"))) == stringList)
    }
  }

  describe("when we want to duplicate numbers N times") {
    it("should be ok") {
      assert(Problems.duplicatedN(3, List("a", "b", "c")) == List("a", "a", "a", "b", "b", "b", "c", "c", "c"))
    }
  }

