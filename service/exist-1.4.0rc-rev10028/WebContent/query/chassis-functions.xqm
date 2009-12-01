module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" ;


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;


(:~
 : TODO describe this module
 :
 : @author Alistair Miles <alimanfoo@gmail.com>
 : 
 :)
 
 
 
(:~
 : Return the value of the edit link href attribute.
 :
 : @param $entry The atom entry to return the URI for
 : 
 : @return the value of the atom edit link href attribute
 :)
declare function chassis:entry-uri( 
    $entry as element(atom:entry) 
    ) as xs:anyURI 
{

    $entry/atom:link[@rel = 'edit']/@href

};




(:~
 : TODO doc me
 :)
declare function chassis:get-entry-by-uri(
    $uri as xs:anyURI,
    $collection as xs:string
    ) as element(atom:entry)?
{

    collection($collection)//atom:entry[chassis:entry-uri(.) = $uri]

};




(:~
 : TODO doc me
 :)
declare function chassis:has-link(
    $subject as element(atom:entry),
    $rel as xs:string,
    $object as element(atom:entry)
    ) as xs:boolean 
{
      
    if ($subject/atom:link[@rel = $rel and @href = chassis:entry-uri($object)]) then true() else false()
    
};



(:~
 : TODO doc me
 :)
declare function chassis:link-objects(
    $subject as element(atom:entry),
    $rel as xs:string,
    $collection as xs:string
    ) as element(atom:entry)* 
{
    
    for $link in $subject/atom:link
    where $link/@rel = $rel
    return chassis:get-entry-by-uri($link/@href, $collection)
    
};




(:~
 : TODO doc me
 :)
declare function chassis:link-subjects(
    $object as element(atom:entry),
    $rel as xs:string,
    $collection as xs:string
    ) as element(atom:entry)*
{
    
    for $subject in collection($collection)//atom:entry
    where chassis:has-link($subject, $rel, $object)
    return $subject

};




declare function chassis:expand-links(
    $subject as element(atom:entry),
    $rel as xs:string,
    $collection as xs:string
    ) as element(atom:link)*
{

    for $link in $subject/atom:link
    where $link/@rel = $rel
    return chassis:expand-link($link, $collection)
    
};





declare function chassis:expand-link( 
    $link as element(atom:link),
    $collection as xs:string
    ) as element(atom:link) {

    let $rel := $link/@rel
	let $href := $link/@href
	return 
	<atom:link rel="{$rel}" href="{$href}">
		{
			chassis:get-entry-by-uri($href, $collection)
		}
	</atom:link>
};




declare function chassis:expand-rev-links(
    $object as element(atom:entry),
    $rel as xs:string,
    $rev as xs:string,
    $collection as xs:string
    ) as element(atom:link)*
{

    for $subject in chassis:link-subjects($object, $rel, $collection)
    return 
    <atom:link rel="{$rev}" href="{chassis:entry-uri($subject)}">
        {
            $subject
        }
    </atom:link>
    
};







