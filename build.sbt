import Dependencies._


val commonSettings = Seq(
  organization := "com.ruiandrebatista.godiva",
  version := "0.1.0",
  scalaVersion := "2.11.7",
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
  ))

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(core, slickProject,sprayProject, playJsonProject)

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(name := "godiva-core")

lazy val slickProject = (project in file("slick"))
  .settings(commonSettings: _*)
  .settings(
    name := "godiva-slick",
    libraryDependencies += slick % "provided")
  .dependsOn(core)

lazy val playJsonProject = (project in file("play-json"))
  .settings(commonSettings:_*)
  .settings(
  name := "godiva-play-json",
  libraryDependencies += playJson % "provided"
  )
  .dependsOn(core)

  
lazy val sprayProject = (project in file("spray"))
  .settings(commonSettings: _*)
  .settings(
    name := "godiva-spray",
    libraryDependencies ++= Seq(sprayRouting % "provided", playJson % "provided"))
    .dependsOn(core, playJsonProject)
