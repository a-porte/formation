import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.must.Matchers.{a, be, thrownBy}
import org.scalatest.matchers.should
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.propspec.AnyPropSpec

class MyAnyPropSpec extends AnyPropSpec with TableDrivenPropertyChecks{
  val toTest = Table(
    "Collection",
    List.empty[Int],
    LazyList.empty[Int],
    Set.empty[Int],
    Seq.empty[Int]
  )

  property("an empty collection should have 0 size") {
    forAll(toTest) { c => c.size should be (0)}
  }

  property("") {
    forAll(toTest) {
      c => a [NoSuchElementException] should be thrownBy {
        c.head
      }
    }
  }

}
