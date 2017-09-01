name := "SimpleBlockchain"

version := "0.1"

scalaVersion := "2.12.3"

scalacOptions += "-Xlint:_"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.postgresql" % "postgresql" % "42.1.3",
  "com.twitter" %% "finatra-http" % "2.11.0"
)
        