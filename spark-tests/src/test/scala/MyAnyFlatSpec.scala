import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{be, contain}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

/*
  Second simplest way to write test suits : there is no nested structure and the 'should' (or 'must' or 'can') keyword must be used
 */
class MyAnyFlatSpec extends AnyFlatSpec{

  "there" should "be 4 rows in the df" in {
    DfHolder.dfCount should be (4)

  }

  "the element with a sortColumn of 4" should "be equal to 16" in {
    DfHolder.dfSelected.collectAsList() should contain only 16
    //assert(DfHolder.dfSelected.collectAsList() == 16)
  }

}
