(: namespace declarations :)
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;

(: find studies colletion :) 
let $studies := collection("/db/studies")

(: fish out request params :)
let $authoremail := request:get-parameter("authoremail","")

(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($studies//atom:entry)
		
		(: filter by author email, if request parameter is given :)
		where ($authoremail != "" and $e/atom:author/atom:email = $authoremail) or ($authoremail = "")

		(: order by most recently updated :)
		order by $e/atom:updated descending
		
		return $e
	}
</atom:feed>