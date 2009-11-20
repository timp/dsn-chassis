(: 
    A script implementing a simple REST service to query and retrieve entries
    in the datafiles collection.
:)


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace my = "http://www.cggh.org/2009/chassis/xquery-function" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: function declarations :)


declare function my:include-media( $link as element() ) as element() {
	let $rel := $link/@rel
	let $href := $link/@href
	return 
	<atom:link rel="{$rel}" href="{$href}">
		{
			let $revision := collection("/db/media")//atom:entry[atom:link[@rel="edit" and @href=$href]]
			return $revision
		}
	</atom:link>
};


declare function my:expand-datafile-link( $link as element() ) as element() {
	let $rel := $link/@rel
	return if ($rel = "chassis.revision") then my:include-media($link) else $link
};


declare function my:expand-datafile( $entry as element() ) as element() {
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
		for $link in $entry/atom:link return my:expand-datafile-link($link),
		my:rev-dataset-links($entry)
	}
	</atom:entry>
};

declare function my:rev-dataset-links( $entry as element() ) as element()* {
    let $href := $entry/atom:link[@rel="edit"]/@href
    for $dataset in collection("/db/datasets")//atom:entry[atom:link[@rel="chassis.datafile" and @href=$href]]
    return
    <atom:link rel="chassis.dataset" href="{$dataset/atom:link[@rel='edit']/@href}">
        { $dataset }
    </atom:link>
};


(: find collection :) 

let $datafiles := collection("/db/datafiles")

(: fish out request params :)
let $param-authoremail := request:get-parameter("authoremail","")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($datafiles//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		
		( ($param-authoremail != "" and $e/atom:author/atom:email = $param-authoremail) or ($param-authoremail = "") )

		and
		
		(: filter by ID, if request parameter is given :)

		( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
		(: order by most recently updated :)

		order by $e/atom:updated descending
		
		return my:expand-datafile($e)
	}
</atom:feed>