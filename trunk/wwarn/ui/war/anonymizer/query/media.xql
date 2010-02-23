
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;

(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;

(: TODO: Finish composing this query, so that it returns the appropriate results. :)

let $param-reviewed := request:get-parameter("reviewed", "")
let $param-reviewOutcome := request:get-parameter("reviewOutcome", "")
let $param-cleaned := request:get-parameter("reviewOutcome", "")

(: return an Atom feed document :)
return
	<atom:feed>
		<atom:title>Query Results</atom:title>
		{
					for $mediaEntry in collection("/db/media")//atom:entry
					order by $mediaEntry/atom:published
					return
					<atom:entry>
						{
						    $mediaEntry/atom:id,
						    $mediaEntry/atom:published,
						    $mediaEntry/atom:author,
						    $mediaEntry/atom:title,
						    $mediaEntry/atom:content,
						    $mediaEntry/atom:category,
						    $mediaEntry/atom:summary,
						    $mediaEntry/atom:link[@rel="edit-media"]
						}
					</atom:entry>
		}
	</atom:feed>