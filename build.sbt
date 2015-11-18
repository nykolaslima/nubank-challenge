name := "nubank-challenge"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.twitter.finatra" %% "finatra-http" % "2.1.1",
  "com.twitter.finatra" %% "finatra-slf4j" % "2.1.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

resolvers ++= Seq(
  "Twitter" at "http://maven.twttr.com"
)