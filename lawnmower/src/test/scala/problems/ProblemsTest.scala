package problems

import org.scalatest.funspec.AnyFunSpec
import problems.Problems.{isCoprimeTo, isPrime, totient, primeFactors}

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

  describe("when we want to drop every Nth elements") {
    it("should be ok") {
      assert(Problems.drop(3, List("a", "b", "c", "d", "e", "f", "g")) == List("a", "b", "d", "e", "g"))
    }
  }
  describe("when we want to split a list") {
    it("should be ok") {
      assert(Problems.split(3, List("a", "b", "c", "d", "e", "f", "g")) == (List("a", "b", "c"), List("d", "e", "f", "g")))
    }
  }
  describe("when we want to extract element from a list to create a new one") {
      it("should be ok") {
        assert(Problems.slice(3, 5, List("a", "b", "c", "d", "e", "f", "g")) == List("d", "e", "f"))
      }
  }

  describe("when we want to translate elements in a list ") {
    it("should accept positive input") {
      assert(Problems.rotate(3, List("a", "b", "c", "d", "e", "f", "g")) == List("d", "e", "f", "g", "a", "b", "c"))
    }
    it ("should accept negative input") {
      assert(Problems.rotate(-2, List("a", "b", "c", "d", "e", "f", "g")) == List("f", "g", "a", "b", "c", "d", "e"))
    }
    it ("should accept positive input and deal recursively with it") {
      assert(Problems.rotate(3, List("a", "b", "c", "d", "e", "f", "g"), isRec = true) == List("d", "e", "f", "g", "a", "b", "c"))
    }
    it("should accept negative input and deal recursively with it") {
      assert(Problems.rotate(-2, List("a", "b", "c", "d", "e", "f", "g"), isRec = true) == List("f", "g", "a", "b", "c", "d", "e"))
    }
  }

  describe("when we want to insert a item to a specific index inside a list") {
    it("should be ok"){
      assert(Problems.insertAt("99", 2, List("a", "b", "c", "d", "e", "f", "g")) == List("a", "b", "99", "c", "d", "e", "f", "g") )
    }
    it("should be ok with a recursive algo") {
      assert(Problems.insertAt("99", 2, List("a", "b", "c", "d", "e", "f", "g"), isRec = true) == List("a", "b", "99", "c", "d", "e", "f", "g") )
    }
  }

  describe("when we want to sort sublists according to their respective length") {
    it("should be ok") {
      assert(Problems.lSort(List(List("a", "b", "c", "d"), List("e", "f", "g"), List("h"))) == List(List("h"), List("e", "f", "g"),List("a", "b", "c", "d")))
    }
  }

  describe("when we want to sort sublists according to their respective length's frequency") {
    it("should be ok") {
      assert(Problems.lSortFreq(List(List("a"), List("a", "b", "c", "d"), List("a"), List("e", "f", "g"), List("a"), List("h"), List("a", "b", "c", "d"))) ==
        List(List("e", "f", "g"), List("h"), List("a", "b", "c", "d"), List("a", "b", "c", "d"), List("a"), List("a"), List("a") ))
    }
  }

  describe("when we want to extends Int class ") {
    describe("and compute 'primeness' of a number") {
      it("should fail for non prime number") {
        assert(!2147483646.isPrime)
      }
      it("should be ok for prime numbers") {
        assert(2147483647.isPrime)
      }
      it ("should be ok for 21") {
        assert(21.isPrime)
      }
    }

    describe("when we want to compute GCD of two numbers") {
      it("should compute correctly") {
        assert(Problems.gcd(63, 36) == 9)
      }
    }
    describe("when we want to know if numbers are coprime from one another") {
      it("should say so") {
        assert(49.isCoprimeTo(64))
      }
      it("should be return false is there are not") {
        assert(!10.isCoprimeTo(20))
      }
    }

    describe("when we want to know the value of the totient / Euler's phi function") {
      it("should be computed") {
        assert(9.totient == List(1, 2, 4, 5, 7, 8).length)
      }
    }

    describe("when we to compute prime factors of a positive inter") {
      it("they should be computed") {
        assert(315.primeFactors === List(3, 3, 5, 7))
      }
    }


  }