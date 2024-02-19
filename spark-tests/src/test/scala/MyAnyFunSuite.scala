import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.matchers.should.Matchers.be

/*
  Simplest way to write a test suite : rather straitghtforward
 */
class MyAnyFunSuite extends AnyFunSuite {


  test("df should hold 4 Rows elements") {
    DfHolder.dfCount should be (4)
  }

  test("item counter with sortColumn equal to 4 should be equal to 16") {
    DfHolder.dfSelected.collectAsList()  should contain only 16
  }

}
