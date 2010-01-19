
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;

(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


declare function local:output-entry(
    $entry as element(atom:entry)
    ) as element(atom:entry) 
{

	let $type := $entry/atom:content/@type
	return
		<atom:entry>
			{
			    $entry/atom:id,
                $entry/atom:published,
			    $entry/atom:author,
			    for $link in $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/originStudy"]
			    return 
			        <atom:link rel="{$link/@rel}" href="{$link/@href}">
			            {
			                for $study in chassis:studies()
			                where $study/atom:link[@rel="self"]/@href = $link/@href
			                return $study
			            }
			        </atom:link>
			    ,
                for $link in $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart"]
                return 
                    <atom:link rel="{$link/@rel}" href="{$link/@href}">
                        {
                            for $media in chassis:media()
                            where $media/atom:link[@rel="self"]/@href = $link/@href
                            return $media
                        }
                    </atom:link>
			}
		</atom:entry>
};



let $username := request:get-attribute("username")
let $param-id := request:get-parameter("id", "")


(: return an Atom feed document :)
return
	<atom:feed>
		<atom:title>Query Results</atom:title>
		{
			for $e in chassis:submissions()
			where ( $e/atom:author/atom:email = $username ) 
			and ( ($param-id != "" and $param-id = $e/atom:id) or $param-id = "" )
			order by $e/atom:published
			return local:output-entry($e)
		}
	</atom:feed>