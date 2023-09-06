lazy val root = (project in file("."))
  .settings(
    name := "string-cal",
    version := "0.1",

    scalaVersion := "3.1.0",

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-funspec" % "3.2.16" % "test"
    ),

    scalacOptions ++= Seq("-Xfatal-warnings")
  )