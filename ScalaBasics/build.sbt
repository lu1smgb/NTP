ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaBasics"
  )

libraryDependencies += "io.github.hughsimpson" %% "scalameter" % "0.22.1"

libraryDependencies += "org.creativescala" %% "doodle" % "0.30.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6.1"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.18.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19"
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0"
