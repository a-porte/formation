import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.{be, convertToAnyShouldWrapper, only}

class MyAnyFunSpec extends AnyFunSpec {

  describe("The dataframe") {
    it("should have 4 rows") {
      DfHolder.dfCount should be (4)
    }
  }

  describe("The counter where sortColumn is 4") {
    it("should be 16") {
      DfHolder.dfSelected.collectAsList() should contain only 16

    }
  }
}
