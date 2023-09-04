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



