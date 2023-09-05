package calculator

import org.scalatest.funspec.AnyFunSpec

class StringCalculatorTest extends AnyFunSpec :
  describe("a StringCal") {
    describe("when receiving an empty String") {
      it("should return 0") {
        assert(StringCalculator().add() == "0")
      }
    }

    describe("when dealing with a single digit"){
     it("should return it "){
       assert(StringCalculator().add("1") == "1")
     }
    }

    describe(" when receiving several digits") {
      it("should use comma as a default separator for 2 numbers"){
        assert(StringCalculator().add("3,4") == "7")
      }
      it("should use comma as a default separator for n numbers") {
        assert(StringCalculator().add("3,4,5,6,7") == "25")
      }
      describe("with a line feed between them") {
        it("should consider it as a standard separator") {
          assert(StringCalculator().add("1\n2,3") == "6")
        }
      }
      describe("with a custom delimitor") {
        it("should use it to sum numbers") {
          assert(StringCalculator().add("//;\n1,2;3") == "6")
        }
      }

    }

    describe("when receiving numbers that are not allowed :") {
      describe("when there is one negative numbers") {
        it("should throw an exception with a single negative number") {
          val except = intercept[NumberFormatException] {
            StringCalculator().add("-1,2,3")
          }
          assert(except.getMessage == "Les nombres négatifs ne sont pas autorisés")
        }
      }
      describe("when there is several numbers"){
          it ("should throw an other exception with the numbers") {
            val except = intercept[NumberFormatException] {
              StringCalculator().add("-5,2,-10,9")
            }
            assert(except.getMessage == "Les nombres négatifs ne sont pas autorisés : -5, -10")
          }
      }

      describe("when receiving numbers > 1000") {
        it("should ignore them") {
          assert(StringCalculator().add("5,10,1664") == "15")
        }
      }
    }
  }