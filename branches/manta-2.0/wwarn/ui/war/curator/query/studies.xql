
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;


import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;

let $username := request:get-attribute("username")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return
	<atom:feed>
		<atom:title>Query Results</atom:title>
		{
			for $e in chassis:studies()
			where 
		
			(: filter by author email, if request parameter is given :)
			( $e/atom:author/atom:email = $username ) 

			and
		
			(: filter by ID, if request parameter is given :)
			( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
			order by $e/atom:published
			
			return $e
		}
	</atom:feed>
	
	