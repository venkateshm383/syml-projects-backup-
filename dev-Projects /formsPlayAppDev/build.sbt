name := """formsappPlay"""

version := "1.0-SNAPSHOT"

checksums in update := Nil

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.couchbase.client" % "couchbase-client" % "1.4.9",
    "org.json" % "json" % "20140107",
  "com.sendwithus" % "java-client" % "1.6.0",
   "com.debortoliwines.openerp" % "openerp-java-api" % "1.5.0",
   "net.iharder" % "base64" % "2.3.8",
   "org.apache.xmlrpc" % "xmlrpc-common" % "3.1.3",
     "org.codehaus.jettison" % "jettison" % "1.1",
     "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3",
     "org.apache.pdfbox" % "pdfbox" % "1.8.8",
 	 "postgresql" % "postgresql" % "9.1-901.jdbc4",
       "org.hibernate" % "hibernate-search" % "5.5.2.Final",
       "org.hibernate" % "hibernate-entitymanager" % "5.0.0.Final",
       "org.hibernate" % "hibernate-annotations" % "3.5.6-Final",
       "org.hibernate" % "hibernate-commons-annotations" % "3.2.0.Final",
   	"com.sun.jersey" % "jersey-bundle" % "1.19",
    	"com.couchbase.client" % "java-client" % "2.2.5"
       
   
  
)


resolvers += "repo" at "https://github.com/sendwithus/sendwithus-mvn-repo/raw/master/releases"

resolvers += "xsalefter maven repository" at "https://github.com/xsalefter/xsalefter-maven-repo/raw/master/releases"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
