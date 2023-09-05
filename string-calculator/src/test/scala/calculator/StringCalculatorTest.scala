package calculator

import org.scalatest.flatspec.AnyFlatSpec

class StringCalculatorTest extends AnyFlatSpec :
  "empty string" should "equal '0'" in
    assert(StringCalculator().add("") == "0")

  "one single digit" should "be equal to its sum" in
    assert(StringCalculator().add("1") == "1")

  "comma " should "separate two digits that are added" in
    assert(StringCalculator().add("3,4") == "7")

  "comma " should "separate 5 digits that are added" in
    assert(StringCalculator().add("3,4,5,6,7") == "25")

  "line feed " should " be dealt as a delimiter " in
    assert(StringCalculator().add("1\n2,3") == "6")

  "add method " should "support custom delimiter" in
    assert(StringCalculator().add("//;\n1,2;3") == "6")

  "one negative number " should "raise an exception " in {
    val except = intercept[NumberFormatException] {
      StringCalculator().add("-1,2,3")
    }
    assert(except.getMessage == "Les nombres négatifs ne sont pas autorisés")
  }

  "several negative numbers " should "raise a different exception message " in {
    val except = intercept[NumberFormatException] {
    StringCalculator().add("-5,2,-10,9")
    }
    assert(except.getMessage == "Les nombres négatifs ne sont pas autorisés : -5, -10")
  }

  "number > 1 000 " should " be ignored" in
    assert(StringCalculator().add("5,10,1664") == "15")
