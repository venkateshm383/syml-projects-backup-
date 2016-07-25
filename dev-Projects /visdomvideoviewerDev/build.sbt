name := """visdomVideoViewer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

checksums := Seq("")

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
    "com.couchbase.client" % "couchbase-client" % "1.4.9",
      "org.codehaus.jettison" % "jettison" % "1.1",
     "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0"


)


resolvers += "xsalefter maven" at "https://raw.githubusercontent.com/xsalefter/xsalefter-maven-repo/master/releases"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.

