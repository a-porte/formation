val sparkVersion = "3.2.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "live",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "2.13.0",

    libraryDependencies ++= Seq(
    "org.apache.spark" %%"spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-hive" % sparkVersion,
    "org.scalatest" %% "scalatest" % "3.2.11" % Test
    )
  )
