import sbt._

object Dependencies {
  val slickVersion = "3.1.0"
  val sprayVersion = "1.3.3"
  val playJsonVersion = "2.4.2"

  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val sprayRouting = "io.spray" %% "spray-routing-shapeless2" % sprayVersion
  val playJson = "com.typesafe.play" %% "play-json" % playJsonVersion
}
