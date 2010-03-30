
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace chassis = "http://www.cggh.org/2009/chassis/xmlns/" ;


import module namespace cf = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: 
	TODO: The media and review link hrefs need to match. May need to modify the createReview method in the ReviewFileWidget 
:)

declare function local:has-not-been-reviewed( $entry as element(atom:entry) ) as xs:boolean
{

    let $reviews := 
        for $review in collection("/db/atom/content/reviews")
        where 
        
            $review//chassis:review/@type = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview"

            and
            
            $review//atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href = $entry/atom:link[@rel="self"]/@href
            
        
        return 
            $review

(:
    let $reviews := 
        for $review in cf:link-subjects("/db/atom/content/reviews", "http://www.cggh.org/2010/chassis/terms/reviewSubject", $entry)
        where $review//chassis:review/@type = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview"
        return $review
:)

    return 
        if ( count( $reviews ) = 0 ) then true()
        else false()      
    
};




let $param-id := request:get-parameter("id", "")

return
<atom:feed>
    <atom:title>Query Results</atom:title>
    {
        (: for $entry in collection("/db/atom/content/media")//atom:entry :)
        for $entry in cf:submitted-media()
        where local:has-not-been-reviewed($entry) and ($param-id = "" or ($param-id != "" and $param-id = $entry/atom:id))
        order by $entry/atom:published
        return
			<atom:entry>
			{
				$entry/atom:id,
				cf:submission-published($entry),
				$entry/atom:author,
				$entry/atom:title,
				$entry/atom:content,
				$entry/atom:category,
				$entry/atom:summary,
				$entry/atom:link
				
			}
			</atom:entry>
    }
</atom:feed>
