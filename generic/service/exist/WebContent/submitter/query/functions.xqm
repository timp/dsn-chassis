module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" ;


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
 
 
 
declare function chassis:studies() as element(atom:entry)*
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
    
    
    
declare function chassis:media() as element(atom:entry)*
{
    for $entry in collection("/db/media")//atom:entry
    let $absolute-uri := concat(request:get-context-path(), "/atom/edit/media", $entry/atom:link[@rel="edit"]/@href)
    let $absolute-content-src := concat(request:get-context-path(), "/atom/edit/media/", $entry/atom:content/@src)
    let $content-type := $entry/atom:content/@type
    return 
        <atom:entry>
        {
            $entry/atom:id,
            $entry/atom:published,
            $entry/atom:updated,
            $entry/atom:title,
            $entry/atom:summary,
            $entry/atom:author,
            $entry/atom:link[@rel!="edit"],
            $entry/atom:category        
        }
            <atom:link rel="edit" href="{$absolute-uri}"/>
            <atom:link rel="self" href="{$absolute-uri}"/>
            <atom:content src="{$absolute-content-src}" type="{$content-type}"/>
        </atom:entry>
};




declare function chassis:submissions() as element(atom:entry)*
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
    
    
    

    