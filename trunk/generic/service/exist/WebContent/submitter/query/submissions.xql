
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: TODO filter by submitted yes/no :)


declare function local:output-entry(
    $entry as element(atom:entry)
    ) as element(atom:entry) 
{

	let $href := concat(request:get-context-path(), "/atom/edit/submissions", $entry/atom:link[@rel="edit"]/@href)
	let $src := concat(request:get-context-path(), "/atom/edit/submissions/", $entry/atom:content/@src)
	let $type := $entry/atom:content/@type
	return
		<atom:entry>
			{
				$entry/atom:id,
				$entry/atom:published
			}
			<atom:link rel="edit" href="{$href}" type="application/atom+xml"/>
			{
				$entry/atom:link		
			}
			<atom:content src="{$src}" type="{$type}"/>
		</atom:entry>
};



declare function local:submitted(
    $entry as element(atom:entry)
    ) as xs:boolean 
{
    
    let $url := concat(request:get-context-path(), "/atom/edit/submissions", $entry/atom:link[@rel="edit"]/@href)
    let $submission := collection("/db/submissions")//atom:entry[atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart" and @href=$url]]
    return count($submission) > 0
    
};


let $username := request:get-attribute("username")
let $param-submitted := request:get-parameter("submitted", "")


(: return an Atom feed document :)
return
	<atom:feed>
	    <debug>
	        param-submitted: {$param-submitted}
	        submitted
	    </debug>
		<atom:title>Query Results</atom:title>
		{
			(: for all Atom entries within the collection :)
			for $e in collection("/db/submissions")//atom:entry
			where ( $e/atom:author/atom:email = $username ) 
			order by $e/atom:published
			return local:output-entry($e)
		}
	</atom:feed>