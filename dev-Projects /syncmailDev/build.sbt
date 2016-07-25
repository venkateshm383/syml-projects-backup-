name := """syncmail"""

version := "1.0-SNAPSHOT"


checksums in update := Nil
lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "javax.mail" % "javax.mail-api" % "1.5.3",
    "com.couchbase.client" % "couchbase-client" % "1.4.8",
      "org.codehaus.jettison" % "jettison" % "1.1",
      "com.sun.mail" % "imap" % "1.5.4",
       "com.sun.mail" % "javax.mail" % "1.5.3",
       "net.iharder" % "base64" % "2.3.8",
      "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
      "net.logstash.logback" % "logstash-logback-encoder" % "3.4",
            "org.apache.pdfbox" % "pdfbox" % "1.8.8",
            "jmimemagic" % "jmimemagic" % "0.1.2"
      
  )
  
 

resolvers += "xsalefter maven repository" at "https://github.com/xsalefter/xsalefter-maven-repo/raw/master/releases"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.

