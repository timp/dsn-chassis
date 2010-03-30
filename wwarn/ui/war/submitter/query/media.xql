
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


declare function local:submitted(
    $entry as element(atom:entry)
    ) as xs:boolean 
{
    
    let $url := $entry/atom:link[@rel="edit"]/@href
    let $submissions := collection("/db/atom/content/submissions")//atom:entry[atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart" and @href=$url]]
    return count($submissions) > 0
    
};


let $username := request:get-attribute("user-name")
let $param-submitted := request:get-parameter("submitted", "")


(: return an Atom feed document :)
return
	<atom:feed>
		<atom:title>Query Results</atom:title>
		{
			for $e in collection("/db/atom/content/media")//atom:entry
			where ( $e/atom:author/atom:email = $username ) 
			and ( ( $param-submitted = "yes" and local:submitted($e) ) or ( $param-submitted = "no" and not(local:submitted($e)) ) or ( $param-submitted != "no" and $param-submitted != "yes" ) )
			order by $e/atom:published
			return $e
		}
	</atom:feed>