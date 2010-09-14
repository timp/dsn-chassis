
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace chassis = "http://www.cggh.org/2009/chassis/xmlns/" ;


import module namespace cf = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


declare function local:has-failed-review( $entry as element(atom:entry) ) as xs:boolean
{
    let $failed-reviews :=
        for $review in collection("/db/atom/content/reviews")//atom:entry
        where 
            $review//chassis:review/@type = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview"
            and
            $review//atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href = $entry/atom:link[@rel="self"]/@href
            and
            $review//chassis:outcome = "fail"
        return $review
            
    return 
        if ( count($failed-reviews) > 0 ) then true() else false()        
};


declare function local:has-not-been-cleaned( $entry as element(atom:entry) ) as xs:boolean
{
    (: we're looking for derivations 
        where the type is RemovePersonalData 
        and the input is the media entry in question :)
        
    let $cleaning-derivations :=
        for $derivation in collection("/db/atom/content/derivations")//atom:entry
        where $derivation//chassis:derivation/@type = "http://www.cggh.org/2010/chassis/terms/RemovePersonalData"
        and $derivation//atom:link[@rel="http://www.cggh.org/2010/chassis/terms/derivationInput"]/@href = $entry/atom:link[@rel="self"]/@href
        return $derivation
        
    return 
        if ( count($cleaning-derivations) = 0 ) then true() else false()

};

declare function local:review-published( $entry as element(atom:entry) )
{

			for $review in collection("/db/atom/content/reviews")//atom:entry
	        where 
	            $review/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href = $entry/atom:link[@rel="self"]/@href
	            
	        return 
	            <chassis:review-published>{$review/atom:published/text()}</chassis:review-published>

			
};

declare function local:review-summary( $entry as element(atom:entry) )
{

			for $review in collection("/db/atom/content/reviews")//atom:entry
	        where 
	            $review/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href = $entry/atom:link[@rel="self"]/@href
	            
	        return 
	            <chassis:review-summary>{$review/atom:summary/text()}</chassis:review-summary>

			
};

let $param-id := request:get-parameter("id", "")

return
<atom:feed>
    <atom:title>Query Results</atom:title>
    {
        (: for $entry in collection("/db/media")//atom:entry :)
        for $entry in cf:submitted-media()
        where local:has-failed-review($entry)
        and local:has-not-been-cleaned($entry)
        and ($param-id = "" or ($param-id != "" and $param-id = $entry/atom:id))
        order by $entry/atom:published
        return
			<atom:entry>
			{
				$entry/atom:id,
				cf:submission-published($entry),
				local:review-published($entry),
				$entry/atom:author,
				$entry/atom:title,
				$entry/atom:content,
				$entry/atom:category,
				$entry/atom:summary,
				local:review-summary($entry),
				$entry/atom:link
				
			}
			</atom:entry>
    }
</atom:feed>
