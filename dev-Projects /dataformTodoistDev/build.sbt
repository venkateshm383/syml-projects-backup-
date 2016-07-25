name := """DataFromToDoist"""

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
  "org.freemarker" % "freemarker" % "2.3.20",
  "com.google.code.gson" % "gson" % "2.3.1",
  "net.logstash.logback" % "logstash-logback-encoder" % "3.4"
)

resolvers += "xsalefter maven" at "https://raw.githubusercontent.com/xsalefter/xsalefter-maven-repo/master/releases"

