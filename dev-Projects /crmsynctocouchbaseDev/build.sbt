name := """crmsynctocouchbase"""


version := "1.0-SNAPSHOT"

checksums in update := Nil

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.couchbase.client" % "couchbase-client" % "1.4.8",
  "org.json" % "json" % "20140107",
  "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
  "javax.mail" % "mail" % "1.4",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.freemarker" % "freemarker" % "2.3.20",
  "org.codehaus.jettison" % "jettison" % "1.1",
  "net.logstash.logback" % "logstash-logback-encoder" % "3.4",
    "org.slf4j" % "slf4j-log4j12" % "1.7.12",
  "org.fluentd" % "fluent-logger" % "0.3.1",
  "ch.qos.logback" % "logback-classic" % "1.0.7",
  "com.sndyuk" % "logback-more-appenders" % "1.1.0",
  "com.sun.jersey" % "jersey-bundle" % "1.19"
  )

resolvers += "xsalefter maven" at "https://raw.githubusercontent.com/xsalefter/xsalefter-maven-repo/master/releases"

resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "Logback more appenders" at "http://sndyuk.github.com/maven"


