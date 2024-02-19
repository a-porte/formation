import org.apache.spark.sql.DataFrame
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec

class MyAnyFeatureSpec extends AnyFeatureSpec with GivenWhenThen{

  info("As a user I want to be able")
  info("to count the number of items in the df")

  private val expectedNb = 2

  Feature("Interact with the df") {
    Scenario("we count the number of elements inside the df") {
      Given("an already existing df")
      val df = DfHolder.df
      assert(df.isInstanceOf[DataFrame])

      When("we count it")
      val nb = df.count()

      Then(s"we should obtain the value '$expectedNb'")
      assert(nb == expectedNb)

    }
  }
}
