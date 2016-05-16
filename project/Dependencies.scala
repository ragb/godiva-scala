import sbt._

object Dependencies {
  val slickVersion = "3.1.1"
  val sprayVersion = "1.3.3"
  val playJsonVersion = "2.5.1"

  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val sprayRouting = "io.spray" %% "spray-routing-shapeless2" % sprayVersion
  val playJson = "com.typesafe.play" %% "play-json" % playJsonVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http-experimental" % "2.0.3"
  val akkaHttpJson = "de.heikoseeberger" %% "akka-http-play-json" % "1.6.0"

  val specs2 = "org.specs2" %% "specs2-core" % "3.7.2"
}
