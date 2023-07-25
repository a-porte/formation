package scalafunctionalkoans

import java.util.NoSuchElementException

import scalafunctionalkoans.support.BlankValues._
import scalafunctionalkoans.support.KoanSuite

class AboutImmutableMaps extends KoanSuite {

  koan("Map behaves like a function") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    nameById(1) should be ("Peter")
    nameById(2) should be ("John")
    nameById(3) should be ("Mary")

    intercept[NoSuchElementException] {
      nameById(100)
    }
  }

  koan("Map behaves like a partial function") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    nameById.isDefinedAt(1) should be (true)
    nameById.isDefinedAt(2) should be (true)
    nameById.isDefinedAt(3) should be (true)

    nameById.isDefinedAt(100) should be (false)
  }

  koan("Map can return an optional value") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    nameById.get(1) should be (Some("Peter"))
    nameById.get(2) should be (Some("John"))
    nameById.get(3) should be (Some("Mary"))

    nameById.get(100) should be (None)
  }

  koan("An entry can be added to a map and return a new map") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val newNameById = nameById + (4 -> "Lara")

    nameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary"))
    newNameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary", 4 -> "Lara"))
  }

  koan("Entries can be added to a map and return a new map") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val newNameById = nameById ++ Seq(4 -> "Lara", 5 -> "Agatha")

    nameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary"))
    newNameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary", 4 -> "Lara", 5 -> "Agatha"))
  }

  koan("An entry can be removed from a map and return a new map") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val newNameById = nameById - 3

    nameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary"))
    newNameById should be (Map(1 -> "Peter", 2 -> "John"))
  }

  koan("Entries can be removed from a map and return a new map") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val newNameById = nameById -- Seq(2, 3)

    nameById should be (Map(1 -> "Peter", 2 -> "John", 3 -> "Mary"))
    newNameById should be (Map(1 -> "Peter"))
  }

  koan("Maps can be filtered like a collection of pairs") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val filteredByKey = nameById.filter({ case (id, name) => id < 3 })
    val filteredByValue = nameById.filter({ case (id, name) => name.startsWith("M") })

    filteredByKey should be (Map(1 -> "Peter", 2 -> "John"))
    filteredByValue should be (Map(3 -> "Mary"))
  }

  koan("Maps can be mapped over like a collection of pairs") {
    val nameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val keyAltered = nameById.map({ case (id, name) => id.toString -> name })
    val valueAltered = nameById.map({ case (id, name) => id -> name.length })
    val idByName = nameById.map({ case (id, name) => name -> id })

    keyAltered should be (Map("1" -> "Peter", "2" -> "John", "3" -> "Mary"))
    valueAltered should be (Map(1 -> 5, 2 -> 4, 3 -> 4))
    idByName should be (Map("Peter" -> 1, "John" -> 2, "Mary" -> 3))
  }

  koan("Maps can be flat-mapped over like a collection of pairs") {
    val firstNameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")

    val fullNameById = firstNameById.flatMap({
      case (firstNameId, firstName) =>
        Map(
          firstNameId + 10 -> s"$firstName Jones",
          firstNameId + 20 -> s"$firstName Simpson"
        )
    })

    fullNameById should be (Map(11 -> "Peter Jones", 12 -> "John Jones", 13 -> "Mary Jones",
      21 -> "Peter Simpson", 22 -> "John Simpson", 23 -> "Mary Simpson"))
  }

  koan("Maps can be used in a `for` comprehension") {
    val firstNameById = Map(1 -> "Peter", 2 -> "Paul", 3 -> "Mary")
    val lastNameById = Map(1 -> "Jones", 2 -> "Simpson", 3 -> "Jackson")

    val fullNameById = for {
      (firstNameId, firstName) <- firstNameById // first generator, assigment patterm matching
      if firstName.startsWith("P") // first filter
      (lastNameId, lastName) <- lastNameById // second generator, assigment patterm matching
      if lastName.startsWith("J") // second filter
    } yield firstNameId * 10 + lastNameId -> s"$firstName $lastName"

    fullNameById should be (Map(11 -> "Peter Jones", 13 -> "Peter Jackson",
      21 -> "Paul Jones", 23 -> "Paul Jackson"))
  }
}
