import Dependencies._

val commonSettings = Seq(
  organization := "com.ruiandrebatista.godiva",
  scalaVersion := "2.12.4",
crossScalaVersions := Seq("2.11.11", "2.12.4"),
  scalacOptions in Compile ++= Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-unchecked",
    "-feature",
    "-Xlint",
//    "-Ywarn-unused-import",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Xmax-classfile-name", "255" //due to pickling macros
  ),
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))

lazy val root = (project in file("."))
  .settings(publish := {})
  .aggregate(core, slickProject, playJsonProject, akkaHttpProject)

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(name := "godiva-core")

lazy val slickProject = (project in file("slick"))
  .settings(commonSettings: _*)
  .settings(
    name := "godiva-slick",
    libraryDependencies += slick % "provided",
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full))
  .dependsOn(core)

lazy val playJsonProject = (project in file("play-json"))
  .settings(commonSettings:_*)
  .settings(
  name := "godiva-play-json",
  libraryDependencies += playJson % "provided"
  )
  .dependsOn(core)

lazy val akkaHttpProject = (project in file("akka-http"))
  .settings(commonSettings: _*)
  .settings(
    name := "godiva-akka-http",
    libraryDependencies ++= Seq(akkaHttp % "provided", playJson % "provided", akkaHttpJson % "provided"))
    .dependsOn(core, playJsonProject)
