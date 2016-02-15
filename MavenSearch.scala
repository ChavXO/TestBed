import java.net._ // class URL for REST requests, see https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
import java.time._ // for time parsing, see https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
import scala.util.parsing.json._ // for json parsing, see http://manuel.bernhardt.io/2015/11/06/a-quick-tour-of-json-libraries-in-scala/
import java.net.HttpURLConnection
import java.util.Scanner
import scala.util.matching.Regex


object MavenSearch {
  case class CoordinateResult(
    groupId: String,
    artifactId: String,
    version: String,
    timestamp: LocalDateTime
  )

  case class ClassnameResult(
    groupId: String,
    artifactId: String,
    latestVersion: String,
    timestamp: Double,
    versionCount: Double
  )


  def byCoordinate(): Vector[CoordinateResult] = {
    /*
    example requests
    http://search.maven.org/solrsearch/select?q=g:"joda-time"&rows=100&wt=json&core=gav
    http://search.maven.org/solrsearch/select?q=fc:"org.joda.time.DateTime"&rows=100&wt=json
    */
    ???
  }

  def byClassname(): Vector[ClassnameResult] = {
    /*
    example requests
    http://search.maven.org/solrsearch/select?q=g:"joda-time"&rows=100&wt=json
    */
    ???
  }

  // only works for fc package format
  def searchMaven(packageName : String) :String = {
  	val charset = java.nio.charset.StandardCharsets.UTF_8.name()
  	val coordPattern = """([\w\.]+)""".r
  	val isCoord = packageName match {
  			case coordPattern(_*) => true
  			case _                => false
		}
  	def constructURL () : String = {
  		// TODO: url construction
	    val url = "http://search.maven.org/solrsearch/select" //q=fc:”org.specs.runner.JUnit”&rows=20&wt=json"
		val queryString = packageName
		val rows = "20"
		val wt = "json"
		val query = String.format("q=%s&rows=%s&wt=%s", 
		 URLEncoder.encode(queryString, charset), 
		 URLEncoder.encode(rows, charset),
		 URLEncoder.encode(wt, charset))
		url + "?" + query
  	}
    
  	val searchUrl = constructURL()

	val myURL = new URL(searchUrl)
	// println(url + "?" + query)
	val myURLConnection = myURL.openConnection()
	myURLConnection.connect()
	val stream = myURLConnection.getInputStream()

	// read input stream as string
	val s = new java.util.Scanner(stream).useDelimiter("\\A");
	val res = s.next()
	res
  }

  def main(args : Array[String]) : Unit = {

  	val packages = JSON.parseFull(searchMaven(args(0)))
  	
	val res = packages.get.asInstanceOf[Map[String, Any]]("response").asInstanceOf[Map[String, List[Map[String, Any]]]]("docs")
	//println(res)
	val res1 = res.map(x => ClassnameResult(
		x("g").asInstanceOf[String], 
		x("a").asInstanceOf[String], 
		x("latestVersion").asInstanceOf[String], 
		x("timestamp").asInstanceOf[Double], 
		x("versionCount").asInstanceOf[Double]))
  	res1.map(x => println(x))
  }
}
