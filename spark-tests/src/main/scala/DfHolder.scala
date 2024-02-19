import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.{lag, sum, when}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession, functions}
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}

object DfHolder {
  val sparkSession: SparkSession = SparkSession.builder()
    .appName("spark-test")
    .master("local[*]")
    .getOrCreate()

  sparkSession.sparkContext.setLogLevel("WARN")

  import sparkSession.implicits._

  private val data = Seq((1,2), (4,5))
  val columns: List[String] = List("one", "two")
  val df: DataFrame = data.toDF(columns: _*)

  private val rowsRdd = sparkSession.sparkContext.parallelize(
    Seq(
      Row(1, 1, 201703, 2),
      Row(2, 1, 201704, 3),
      Row(3, 1, 201705, 7),
      Row(4, 1, 201706, 6)))

  private val schemaNew: StructType = new StructType()
    .add(StructField("sortColumn", IntegerType, nullable = false))
    .add(StructField("id", IntegerType, nullable = false))
    .add(StructField("month", IntegerType, nullable = false))
    .add(StructField("number", IntegerType, nullable = false))

  private val df0 = sparkSession.createDataFrame(rowsRdd, schemaNew)
  private val winSum = Window.partitionBy("id").orderBy($"sortColumn").rowsBetween(-2, 0)

  private val winLag: WindowSpec = Window.partitionBy("id").orderBy("sortColumn")
  private val countedDf: DataFrame = df0
    .withColumn("countertmp",
      sum($"number").over(winSum))
    .withColumn("lag", lag($"number", 2, 0).over(winLag))
    .withColumn("counter",
      when($"lag" === "0", 0)
        .otherwise($"countertmp")
    ).cache()

  val dfCount: Long = countedDf.count()
  val dfSelected: Dataset[Long] = countedDf.select("counter").where($"sortColumn" === 4).as[Long]

}
