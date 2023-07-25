addCommandAlias("namaste", "~testOnly scalafunctionalkoans.Koans")

lazy val root = (project in file("."))
  .settings(
    name := "scala-functional-koans",
    version := "0.1",

    scalaVersion := "2.11.12",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature"),

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    ),

    test / traceLevel := -1,
    test / parallelExecution := false,
    logLevel := Level.Info,
    showTiming := false,
    showSuccess := false
  )
