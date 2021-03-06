lazy val commonSettings = Seq(
  organization := "com.github.laysakura",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint"),
  version := (version in ThisBuild).value,
  parallelExecution in ThisBuild := false,
  resolvers += ("twitter" at "https://maven.twttr.com")
)

lazy val versions = new {
  val logback = "1.1.7"
  val finagle = "6.40.0"
  val finatra = "2.6.0"
  val scrooge = "4.12.0"

  val guice = "4.0"
  val scalatest = "3.0.1"
  val specs = "2.3.12"
}

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  aggregate(verboseService)

lazy val common = (project in file("common")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % versions.logback,
      "com.twitter" %% "scrooge-core" % versions.scrooge,
      "com.twitter" %% "inject-core" % versions.finatra,
      "com.twitter" %% "inject-app" % versions.finatra,

      "org.specs2" %% "specs2" % versions.specs % "test",
      "org.scalatest" %% "scalatest" % versions.scalatest % "test",

      // for TestInjector
      "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
      "com.twitter" %% "finatra-thrift" % versions.finatra % "test",
      "com.twitter" %% "finatra-thrift" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-app" % versions.finatra % "test",
      "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-core" % versions.finatra % "test",
      "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-modules" % versions.finatra % "test",
      "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-server" % versions.finatra % "test",
      "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests"
    )
  )

lazy val verboseService = (project in file("verboseService")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-thrift" % versions.finagle,
      "com.twitter" %% "finagle-core" % versions.finagle
    )
  ).
  aggregate(verboseServiceIdl).
  dependsOn(verboseServiceIdl % "test->test;compile->compile")

lazy val verboseServiceIdl = (project in file("verboseServiceIdl")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finatra-thrift" % versions.finatra,
      "com.twitter" %% "scrooge-core" % versions.scrooge
    ),
    scroogeThriftSourceFolder in Compile := baseDirectory { base => base / "src/main/thrift" }.value,
    scroogeThriftDependencies in Compile := Seq("finatra-thrift_2.11")
  ).
  aggregate(common).
  dependsOn(common % "test->test;compile->compile")
