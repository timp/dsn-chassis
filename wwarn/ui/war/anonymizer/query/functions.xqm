module namespace cf = "http://www.cggh.org/2009/chassis/xquery-functions" ;


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace chassis = "http://www.cggh.org/2009/chassis/xmlns/" ;


(:~
 : TODO describe this module
 :
 : @author Alistair Miles <alimanfoo@gmail.com>
 : 
 :)
 
 
 
declare function cf:studies() as element(atom:entry)*
{
    collection("/db/atom/content/studies")//atom:entry
};
    
    
    
declare function cf:media() as element(atom:entry)*
{
    collection("/db/atom/content/media")//atom:entry
};




declare function cf:submissions() as element(atom:entry)*
{
    collection("/db/atom/content/submissions")//atom:entry
};
    
    
    
declare function cf:link-subjects( $collection as xs:string , $rel as xs:string , $object as element(atom:entry) ) as element(atom:entry)*
{
    let $object-uri := $object/atom:link[@rel="self"]/@href
    for $subject in collection($collection)//atom:entry
    where $subject/atom:link[@rel=$rel]/@href = $object-uri
    return $subject
};
    
    
declare function cf:is-submitted( $media-entry as element(atom:entry) ) as xs:boolean
{

    let $media-entry-uri := $media-entry/atom:link[@rel="self"]/@href
    let $submissions :=
        for $submission in collection("/db/atom/content/submissions")//atom:entry
        where $submission/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart"]/@href = $media-entry-uri
        return $submission
    
    return exists( $submissions )
        
};


declare function cf:submitted-media() as element(atom:entry)* 
{
    for $entry in cf:media()
    where cf:is-submitted($entry)
    return $entry
};

declare function cf:submission-published( $entry as element(atom:entry) )
{

			for $submission in cf:submissions()
	        where 
	            $submission/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart"]/@href = $entry/atom:link[@rel="self"]/@href
	            
	        return 
	            <chassis:submission-published>{$submission/atom:published/text()}</chassis:submission-published>

			
};
    