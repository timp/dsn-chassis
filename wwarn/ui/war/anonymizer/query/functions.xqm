module namespace cf = "http://www.cggh.org/2009/chassis/xquery-functions" ;


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace request = "http://exist-db.org/xquery/request" ;


(:~
 : TODO describe this module
 :
 : @author Alistair Miles <alimanfoo@gmail.com>
 : 
 :)
 
 
 
declare function cf:studies() as element(atom:entry)*
{
    for $entry in collection("/db/studies")//atom:entry
    let $absolute-uri := concat(request:get-context-path(), "/atom/edit/studies", $entry/atom:link[@rel="edit"]/@href)
    return 
        <atom:entry>
        {
            $entry/atom:id,
            $entry/atom:published,
            $entry/atom:updated,
            $entry/atom:title,
            $entry/atom:summary,
            $entry/atom:author,
            $entry/atom:content,
            $entry/atom:link[@rel!="edit"]
        }
            <atom:link rel="edit" href="{$absolute-uri}"/>
            <atom:link rel="self" href="{$absolute-uri}"/>
        </atom:entry>
};
    
    
    
declare function cf:media() as element(atom:entry)*
{
    for $entry in collection("/db/media")//atom:entry
    let $absolute-uri := concat(request:get-context-path(), "/atom/edit/media", $entry/atom:link[@rel="edit"]/@href)
    let $absolute-content-src := concat(request:get-context-path(), "/atom/edit/media/", $entry/atom:content/@src)
    let $content-type := $entry/atom:content/@type
    let $resource := xs:string($entry/atom:content/@src)
    let $size := xmldb:size("/db/media", $resource)
    return 
        <atom:entry>
        {
            $entry/atom:id,
            $entry/atom:published,
            $entry/atom:updated,
            $entry/atom:title,
            $entry/atom:summary,
            $entry/atom:author,
            $entry/atom:link[@rel!="edit" and @rel!="edit-media"],
            $entry/atom:category        
        }
            <atom:size>                
            {
                if ($size < 1024) then (
                	concat(xs:string(round($size)), " bytes")
                )
                else if ($size < 1048576) then (
                	concat(xs:string(round($size div 1024)), " KB")
                )
                else if ($size < 1073741824) then (
                	concat(xs:string(round($size div 1048576)), " MB")
                )
                else if ($size < 1099511627776) then (
                	concat(xs:string(round($size div 1073741824)), " GB")
                )
                else (
                	concat(xs:string(round($size div 1099511627776)), " TB")
                )
            }
            </atom:size>
            <atom:link rel="edit" href="{$absolute-uri}"/>
            <atom:link rel="self" href="{$absolute-uri}"/>
            <atom:content src="{$absolute-content-src}" type="{$content-type}"/>
        </atom:entry>
};




declare function cf:submissions() as element(atom:entry)*
{
    for $entry in collection("/db/submissions")//atom:entry
    let $absolute-uri := concat(request:get-context-path(), "/atom/edit/submissions", $entry/atom:link[@rel="edit"]/@href)
    return 
        <atom:entry>
        {
            $entry/atom:id,
            $entry/atom:published,
            $entry/atom:updated,
            $entry/atom:title,
            $entry/atom:summary,
            $entry/atom:author,
            $entry/atom:content,
            $entry/atom:link[@rel!="edit"]
        }
            <atom:link rel="edit" href="{$absolute-uri}"/>
            <atom:link rel="self" href="{$absolute-uri}"/>
        </atom:entry>
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
        for $submission in collection("/db/submissions")//atom:entry
        where $submission/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/submissionPart"]/@href = $media-entry-uri
        return $submission
    
    return
        if ( count($submissions) > 0 ) then true()
        else false()
        
};


declare function cf:submitted-media() as element(atom:entry)* 
{
    for $entry in cf:media()
    where cf:is-submitted($entry)
    return $entry
};


    