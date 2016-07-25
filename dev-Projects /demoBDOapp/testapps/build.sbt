name := """testapps"""

version := "1.0-SNAPSHOT"


checksums in update := Nil

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  javaWs,"com.sendwithus" % "java-client" % "1.6.0",
  "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
  "org.codehaus.jettison" % "jettison" % "1.3.7",
  "com.couchbase.client" % "couchbase-client" % "1.4.10",
    "org.slf4j" % "slf4j-log4j12" % "1.7.12",
    "org.fluentd" % "fluent-logger" % "0.3.1",
 	"ch.qos.logback" % "logback-classic" % "1.0.7",
   "com.sndyuk" % "logback-more-appenders" % "1.1.0"
)

 
resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "xsalefter maven repository" at "https://github.com/xsalefter/xsalefter-maven-repo/raw/master/releases"

resolvers += "Logback more appenders" at "http://sndyuk.github.com/maven"