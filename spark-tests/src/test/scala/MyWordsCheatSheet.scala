import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{AnalysisException, Dataset, Encoders, Row, SparkSession, functions}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.scalatest.BeforeAndAfter
import org.scalatest.Inside.inside
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{be, contain, empty, equal, have, include, matchPattern, noException, not, sorted}
import org.scalatest.matchers.should.Matchers.{an, convertToAnyShouldWrapper, every, the}
import org.scalatest.Inspectors._
import org.scalatest.exceptions.TestFailedException

class MyWordsCheatSheet extends AnyFlatSpec {

    val spark: SparkSession = DfHolder.sparkSession

  import spark.implicits._

  private val df = DfHolder.df

  "dataframe" should " have 4 rows" in {

    DfHolder.dfCount should be (4)

  }

  "testing with properties" should "pass" in {

    df.show()

    inside(df) {
      case _:Dataset[_] =>
        df.count() should be (2)
    }

    df should matchPattern { case _: Dataset[_] => }

    case class MyInts(one: Int, two: Int)
    implicit val encoder = Encoders.kryo[MyInts]
    df.as[(Int,Int)].show()
    //df.select("*").as[MyInts].show() // KO

    val ds = df.as[(Int,Int)]

    ds should matchPattern { case _: Dataset[Row] => }
    df.as[(Int,Int)] should matchPattern { case _: Dataset[(Int, Int)] => }
    //both are ok because of type erasure ...
  }

  "dealing with exception" should "be ok" in {
    val invalidColName = "invalid"
    an[AnalysisException] should be thrownBy df.select(invalidColName) // if the expectation is not met, then a TestFailedException is thrown
    val eThrown = the[AnalysisException] thrownBy df.select(invalidColName) // to be able to test what has been thrown
    //eThrown.message should include (s"cannot resolve '$invalidColName'") // should be ok but there is a problem with the implicits
    convertToAnyShouldWrapper(eThrown.message) should
      equal(s"cannot resolve '$invalidColName' given input columns: [${DfHolder.columns.mkString(", ")}]")

    // can also be done in a single statement
    //the [AnalysisException] thrownBy df.select("invalid") should have message s"cannot resolve '$invalidColName' given input columns: [${c.mkString(", ")}]"// can't because of implicits

    /*the [AnalysisException] thrownBy { // same here
      df.select(invalidColName)
    } should have message s"cannot resolve '$invalidColName' given input columns: [${c.mkString(", ")}]"

     */
    noException should be thrownBy df.select(DfHolder.columns.map(col): _*)
  }


  "dealing with \"containers\"" should "be ok" in {
    val s = Set(1,2,3,4,1)

    s should contain (2)
    s(3) should not be 4

    s.size should (be (4) and be < 5) // requires parentheses

    s.filter(_ == 2) should contain only 2

    s should contain oneElementOf s.map(_*4)

    s should contain noElementsOf s.map(_*10)
    s should contain noneOf  (5,6)

  }
  "dealing with \"aggregations\" " should "be ok" in {
    val l = Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four")

    l should contain atLeastOneElementOf Map(5 -> "five", 3 -> "three", 2 -> "two", 6 -> "six")
    l should contain atMostOneElementOf Map(5 -> "five", 3 -> "three", 6 -> "six")
    l should contain allElementsOf Map(2 -> "two")
    l should contain theSameElementsAs Map(1 -> "one", 2 -> "two", 3 -> "three", 4 -> "four")
  }

  "dealing with sequences" should "be ok" in {
    val l = List(1,2,3,4,1)

    l.sortWith(_< _) shouldBe sorted

  }

  "dealing with iterators" should "be ok" in {
    val i = List(1, 2, 3, 4, 1).iterator

    i.to(LazyList) should contain (4)

  }

  "dealing with inpectors" should "be ok" in {
    val l = List(1, 2, 3, 4, 1)

    forAll (l) {e => e should be > 0}


    intercept [TestFailedException]{
      //inspector shorhand with non nested ones
      every (l)  should  be < 0 //in case of error, with forEvery, the message indicates every element e not satisfying the test condition
      /*
      lists of shorthands - non-shorthands
      all - forAll
      atLeast - forAtLeast
      atMost - forAtMost
      between - forBetween
      every - forEvery
      exactly - forExactly
       */
    }

  }

  /*"dealing with Options" should "be ok" {
    val optInt : Option[Int] = Option(5)
    val optNone : Option[Int] = None

    //optInt shouldEqual Some(5)
    //optInt shouldBe defined
    //optNone shouldBe empty

    //optInt should contain (5)

  }*/




}