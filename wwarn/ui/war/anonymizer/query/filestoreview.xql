
(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace chassis = "http://www.cggh.org/2009/chassis/xmlns/" ;


import module namespace cf = "http://www.cggh.org/2009/chassis/xquery-functions" at "functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;




declare function local:has-not-been-reviewed( $entry as element(atom:entry) ) as xs:boolean
{

    let $reviews := 
        for $review in collection("/db/reviews")
        where 
        
            $review//chassis:review/@type = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview"
        
            and
        
            $review/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href = $entry/atom:link[@rel="self"]/@href
            
        return 
            $review

(:
    let $reviews := 
        for $review in cf:link-subjects("/db/reviews", "http://www.cggh.org/2010/chassis/terms/reviewSubject", $entry)
        where $review//chassis:review/@type = "http://www.cggh.org/2010/chassis/terms/PersonalDataReview"
        return $review
:)

    return 
        if ( count( $reviews ) = 0 ) then true()
        else false()      
    
};


declare function local:submission-published( $entry as element(atom:entry) )
{
		let $submission-published :=
			for $submission in cf:submissions()
	        where 
	            $submission/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart"]/@href = $entry/atom:link[@rel="self"]/@href
	            
	        return 
	            $submission/atom:published
		            
		return
			$submission-published
			
};

<atom:feed>
    <atom:title>Query Results</atom:title>
    {
        (: for $entry in collection("/db/media")//atom:entry :)
        for $entry in cf:submitted-media()
        where local:has-not-been-reviewed($entry)
        order by $entry/atom:published
        return
			<atom:entry>
			{
				$entry/atom:id,
				local:submission-published($entry),
				$entry/atom:updated,
				$entry/atom:title,
				$entry/atom:summary,
				$entry/atom:author,
				$entry/atom:category,
				$entry/atom:link,
				$entry/atom:content
			}
			</atom:entry>
    }
</atom:feed>
