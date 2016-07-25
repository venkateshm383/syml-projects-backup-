name := """StageMail"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

checksums := Seq("")


libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.couchbase.client" % "couchbase-client" % "1.4.9",
  "org.json" % "json" % "20140107",
  "javax.mail" % "mail" % "1.4",
  "org.freemarker" % "freemarker" % "2.3.20",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.sendwithus" % "java-client" % "1.6.0",
   "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
  "com.google.code.gson" % "gson" % "2.2.2" 
  )
  
resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "xsalefter maven repository" at "https://github.com/xsalefter/xsalefter-maven-repo/raw/master/releases"
