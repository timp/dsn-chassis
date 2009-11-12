(: 
    A script implementing a simple REST service to query and retrieve entries
    in the datasets collection.
:)


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace my = "http://www.cggh.org/2009/chassis/xquery-function" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: function declarations :)


declare function my:expand-link( $collection-path as xs:string, $link as element() ) as element() {
	let $rel := $link/@rel
	let $href := $link/@href
	return 
	<atom:link rel="{$rel}" href="{$href}">
		{
			collection($collection-path)//atom:entry[atom:link[@rel="edit" and @href=$href]]
		}
	</atom:link>
};



declare function my:expand-dataset-link( $link as element() ) as element() {
	let $rel := $link/@rel
	return 
	    if ($rel = "chassis.study") then my:expand-link("/db/studies", $link) 
	    else if ($rel = "chassis.datafile") then my:expand-link("/db/datafiles", $link)
	    else $link
};



declare function my:expand-dataset( $entry as element() ) as element() {
	let $id := $entry/atom:id
	return 
	<atom:entry>
	{
		$id,
		$entry/atom:published,
		$entry/atom:updated,
		$entry/atom:title,
		$entry/atom:summary,
		$entry/atom:category,
		$entry/atom:author,
		for $link in $entry/atom:link return my:expand-dataset-link($link)
	}
	</atom:entry>
};


(: find collection :) 

let $datasets := collection("/db/datasets")

(: fish out request params :)
let $param-authoremail := request:get-parameter("authoremail","")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($datasets//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		
		( ($param-authoremail != "" and $e/atom:author/atom:email = $param-authoremail) or ($param-authoremail = "") )

		and
		
		(: filter by ID, if request parameter is given :)

		( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
		(: order by most recently updated :)

		order by $e/atom:updated descending
		
		return my:expand-dataset($e)
	}
</atom:feed>