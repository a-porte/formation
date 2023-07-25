package scalafunctionalkoans

import java.util.Date

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

/*
TUPLES

Tuples are a fundamental concept in most functional languages.
*/
class AboutTuples extends KoanSuite {

  koan("Tuples can be created easily") {
    val tuple = ("apple", "dog")

    tuple should be (("apple", "dog"))
  }

  koan("Tuples support equality by value") {
    val t1 = ("apple", "dog")
    val t2 = ("pear", "cat")
    val t3 = ("apple", "dog")

    (t1 == t2) should be (false) // compare by value
    (t1 == t3) should be (true)
    (t1 eq t3) should be (false) // compare reference
  }

  // Not the preferred way of accessing tuple attributes
  koan("Tuple items may be accessed individually") {
    val tuple = ("apple", "dog")
    val fruit = tuple._1
    val animal = tuple._2

    fruit should be ("apple")
    animal should be ("dog")
  }

  // Not the preferred way of accessing tuple attributes
  koan("Tuples may be of mixed type") {
    val tuple5 = ("a", 1, 2.2, new Date(), BigDecimal(5))

    tuple5._2 should be (1)
    tuple5._5 should be (BigDecimal(5))
  }

  koan("Tuples can be matched in assignements") {
    val tuple = ("cat", 5)

    val (species, age) = tuple

    species should be ("cat")
    age should be (5)
  }
}
