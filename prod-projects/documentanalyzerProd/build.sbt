name := """documentanalyzer"""

version := "1.0-SNAPSHOT"

checksums in update := Nil

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.couchbase.client" % "couchbase-client" % "1.4.11",
  "com.couchbase.client" % "java-client" % "2.1.6",
  "org.json" % "json" % "20140107",
  "javax.mail" % "mail" % "1.4",
  "org.freemarker" % "freemarker" % "2.3.20",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.sendwithus" % "java-client" % "1.6.0",
  "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
  "com.google.code.gson" % "gson" % "2.2.2",
  "org.apache.pdfbox" % "pdfbox" % "1.8.10",
  "net.iharder" % "base64" % "2.3.8",
    "com.sun.jersey" % "jersey-bundle" % "1.19",
     "org.slf4j" % "slf4j-log4j12" % "1.7.12",
    "org.fluentd" % "fluent-logger" % "0.3.1",
 	"ch.qos.logback" % "logback-classic" % "1.0.7",
   "com.sndyuk" % "logback-more-appenders" % "1.1.0"
)


resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "xsalefter maven" at "https://raw.githubusercontent.com/xsalefter/xsalefter-maven-repo/master/releases"

resolvers += "Logback more appenders" at "http://sndyuk.github.com/maven"

