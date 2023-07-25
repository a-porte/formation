package scalafunctionalkoans

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

/*
OPTIONS (AND IT'S NONE)

An option allows to model the presence or the absence of one value.

It is a functional substitute to `null`.
`null` should be completely avoided in functional style,
but is kept for Java compatibility.

To some extent, it is similar to a collection
that would hold either 1 or 0 element.
It can thus be filtered, mapped or flat mapped.

`None` is a special value meaning the absence of a value.
`Some(x)` allows to model the presence of a value.
*/
class AboutOptions extends KoanSuite {

  koan("Presence of value can be verified") {
    val none: Option[String] = None
    val something: Option[String] = Some("value")

    none.isEmpty should be (true)
    something.isEmpty should be (false)
  }

  koan("It is also possible to use type inference with options") {
    val none = Option.empty[String] // Calling generic `empty` method on `Option` object
    val something = Some("value")

    none.isEmpty should be (true)
    something.isEmpty should be (false)
  }

  koan("Present value can be obtained from option (or not)") {
    val none = Option.empty[String]
    val something = Some("value")

    intercept[NoSuchElementException] {
      none.get
    }

    something.get should be ("value")
  }

  koan("Option can be filtered exactly like a single item collection") {
    Option.empty[Int].filter(_ > 0) should be (None)
    Some(-5).filter(_ > 0) should be (None)
    Some(5).filter(_ > 0) should be (Some(5))
  }

  koan("Option can be mapped over like a single item collection") {
    Option.empty[Int].map(_ + 1) should be (None)
    Some(5).map(_ + 1) should be (Some(6))
  }

  koan("Option can help traverse a property from an optional reference") {
    case class Customer(email: String)

    val noCustomer = Option.empty[Customer]
    val customerWithEmail = Some(Customer(email = "pierre.dupond@valtech.fr"))

    noCustomer.map(_.email) should be (None)
    customerWithEmail.map(_.email) should be (Some("pierre.dupond@valtech.fr"))
  }

  koan("Option can help traverse an optional property from an optional reference") {
    case class Customer(email: Option[String])

    val noCustomer = Option.empty[Customer]
    val customerWithoutEmail = Some(Customer(email = None))
    val customerWithEmail = Some(Customer(email = Some("pierre.dupond@valtech.fr")))

    noCustomer.flatMap(_.email) should be (None)
    customerWithoutEmail.flatMap(_.email) should be (None)
    customerWithEmail.flatMap(_.email) should be (Some("pierre.dupond@valtech.fr"))
  }

  koan("None can be replaced by a default value") {
    Option.empty[Int].getOrElse(0) should be (0)
    Some(5).getOrElse(0) should be (5)
  }

  koan("Options can be used in a `for` comprehension") {
    def fullName(firstName: Option[String], lastName: Option[String]): Option[String] = {
      for {
        _firstName <- firstName
        _lastName <- lastName
      } yield s"${_firstName} ${_lastName}"
    }

    fullName(None, None) should be (None)
    fullName(None, Some("Dupond")) should be (None)
    fullName(Some("Pierre"), None) should be (None)
    fullName(Some("Pierre"), Some("Dupond")) should be (Some("Pierre Dupond"))
  }
}
