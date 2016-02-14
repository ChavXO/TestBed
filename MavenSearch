import java.net._ // class URL for REST requests, see https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
import java.time._ // for time parsing, see https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
import scala.util.parsing.json._ // for json parsing, see http://manuel.bernhardt.io/2015/11/06/a-quick-tour-of-json-libraries-in-scala/
import java.net.HttpURLConnection
import java.util.Scanner


object MavenSearch extends App{
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
    timestamp: LocalDateTime,
    versionCount: Int
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

	//println(res)
	res
  }

  
  println(JSON.parseFull(searchMaven("scalaz")))
}
