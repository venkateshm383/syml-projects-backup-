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
  "com.couchbase.client" % "couchbase-client" % "1.4.8",
  "org.json" % "json" % "20140107",
  "javax.mail" % "mail" % "1.4",
  "org.freemarker" % "freemarker" % "2.3.20",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.sendwithus" % "java-client" % "1.6.0",
  "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
  "com.google.code.gson" % "gson" % "2.2.2",
  "org.apache.pdfbox" % "pdfbox" % "1.8.10",
  "net.iharder" % "base64" % "2.3.8"
)





resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "xsalefter maven" at "https://raw.githubusercontent.com/xsalefter/xsalefter-maven-repo/master/releases"



