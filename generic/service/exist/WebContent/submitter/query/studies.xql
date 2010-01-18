
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;



declare function local:output-entry(
    $entry as element(atom:entry)
    ) as element(atom:entry) 
{

	let $href := concat(request:get-context-path(), "/atom/edit/study", $entry/atom:link[@rel="edit"]/@href)
	let $src := concat(request:get-context-path(), "/atom/edit/study/", $entry/atom:content/@src)
	let $type := $entry/atom:content/@type
	return
		<atom:entry>
			{
				$entry/atom:id,
				$entry/atom:published
			}
			<atom:link rel="edit" href="{$href}" type="application/atom+xml"/>
			{
				$entry/atom:author,
				$entry/atom:title,
				$entry/atom:summary,
				$entry/atom:category		
			}
			<atom:content src="{$src}" type="{$type}"/>
		</atom:entry>
};



let $studies := collection("/db/studies")
let $username := request:get-attribute("username")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return
	<atom:feed>
		<atom:title>Query Results</atom:title>
		{
			for $e in ($studies//atom:entry)
			where 
		
			(: filter by author email, if request parameter is given :)
			( $e/atom:author/atom:email = $username ) 

			and
		
			(: filter by ID, if request parameter is given :)
			( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
			order by $e/atom:published
			
			return local:output-entry($e)
		}
	</atom:feed>
	
	