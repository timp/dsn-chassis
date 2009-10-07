(: namespace declarations :)
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;

(: find submissions colletion :) 
let $datafiles := collection("/db/datafiles")

(: fish out request params :)
let $authoremail := request:get-parameter("authoremail","")
let $submission  := request:get-parameter("submission","")

(: return an Atom feed document :)
return 
<atom:feed>
	<params>
		<authoremail>{$authoremail}</authoremail>
		<submission>{$submission}</submission>
	</params>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($datafiles//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		( ($authoremail != "" and $e/atom:author/atom:email = $authoremail) or ($authoremail = "") )

		(: filter by submission URL, if request parameter is given :)
		and ( ($submission != "" and $e/atom:link[@rel="chassis.submission"]/@href = $submission) or ($submission = "") )
			
		(: order by most recently updated :)
		order by $e/atom:updated descending
		
		return $e
	}
</atom:feed>