import sbt._

object Dependencies {
  val slickVersion = "3.2.1"

  val playJsonVersion = "2.6.0"

  val slick = "com.typesafe.slick" %% "slick" % slickVersion

  val playJson = "com.typesafe.play" %% "play-json" % playJsonVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.11"
  val akkaHttpJson = "de.heikoseeberger" %% "akka-http-play-json" % "1.18.0"

  val specs2 = "org.specs2" %% "specs2-core" % "4.0.2"
}
