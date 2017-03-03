import sbtassembly.Plugin.AssemblyKeys._

name          := "doll"

version       := "0.1"

scalaVersion  := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.specs2" % "specs2-core_2.11" % "3.8.8"
)

assemblySettings
