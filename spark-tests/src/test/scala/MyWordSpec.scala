import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters.ListHasAsScala

class MyWordSpec extends AnyWordSpec{

  "The df" when {
    "its items are counted" should { // where 'should' is of StringShouldWrapperForForVerb
      "have 4 items" in {
        assert(4 == DfHolder.dfCount) //DfHolder.dfCount should be (4) // here 'should' requires the convertToAnyShouldWrapper implicit method
        // here 'should' requires the convertToAnyShouldWrapper implicit method, its import would have collided with the 'should' in line 7
      }
    }
    "filtered over 'sortColumn' and selecting 'count'" should {
      "be equal to 16" in {
        assert(16 == DfHolder.dfSelected.collectAsList().asScala.head)
      }
    }

  }
}
