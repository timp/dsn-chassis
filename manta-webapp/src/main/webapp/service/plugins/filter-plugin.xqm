xquery version "1.0";

module namespace filter-plugin = "http://www.cggh.org/2010/chassis/filter/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace app = "http://www.w3.org/2007/app" ;
declare namespace filter = "http://www.cggh.org/2010/chassis/filter/xmlns" ;
declare namespace manta = "http://www.cggh.org/2010/chassis/manta/xmlns" ;


import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace security-config = "http://purl.org/atombeat/xquery/security-config" at "../config/security.xqm" ;

import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace mime = "http://purl.org/atombeat/xquery/mime" at "../lib/mime.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;

declare variable $filter-plugin:logger-name := "org.cggh.chassis.filter.xquery.atombeat.plugin";


declare function local:log4jDebug(
    $message as item()*
) as empty()
{
  util:log-app( "debug" , $filter-plugin:logger-name , $message ) (: only use within our function :)
};

declare function local:log4jInfo(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $filter-plugin:logger-name , $message ) (: only use within our function :)
};

declare function filter-plugin:get-request-path-info($request as element(request) ) as xs:string
{
    let $request-path-info := $request/path-info/text()
    return $request-path-info
};

declare function filter-plugin:after(
	$operation as xs:string ,
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := filter-plugin:get-request-path-info($request)
	let $message := ( "chassis-filter plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return
		
		if ($operation = $CONSTANT:OP-LIST-COLLECTION) then
	        if ( matches( $request-path-info , "^/media/submitted" ) or matches( $request-path-info , "^/studies" ) ) then 
                filter-plugin:after-list-collection( $request, $response )	
	        else 
	            $response
	    else
	        $response

};

declare function filter-plugin:after-list-collection(
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $feed := $response/body/atom:feed 
    
    let $feed := 
        <atom:feed>
        {        
            $feed/attribute::* ,
            $feed/child::*[ not( . instance of element(atom:entry) ) ],
            
            for $entry in $feed/child::*[. instance of element(atom:entry) ]
                let $pdr := filter-plugin:do-filter-pdr( $request, $entry )
                let $simple := filter-plugin:do-filter-simple( $request, $pdr )
                return $simple
        }
        </atom:feed>
    
    return filter-plugin:replace-response-body( $response , $feed )
    
};

declare function filter-plugin:do-filter-pdr(
    $request as element(request) ,
	$entry as element()
) as element()?
{
    
     let $param-filter := xutil:get-parameter( "filter" , $request )
     
     return if (contains($param-filter, "pdr")) then
     let $link := $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews"]
     
     let $path-info-with-query-string := substring-after( $link/@href , $config:self-link-uri-base )
     let $col-path-info := if ( contains( $path-info-with-query-string , '?' ) ) then substring-before( $path-info-with-query-string , '?' ) else $path-info-with-query-string
     let $query-string := substring-after ( $path-info-with-query-string , '?' )
     let $review :=
                for $component in tokenize( $query-string , '&amp;' )
                return
                    if (substring-before($component, '=') = "reviewSubject") then
                        substring-after( $component, '=' )
                    else 
                        ()
                    
     let $pdr-col := atomdb:retrieve-feed( $col-path-info )
                  

     let $pdr := $pdr-col/atom:entry[atom:link[@rel='http://www.cggh.org/2010/chassis/terms/reviewSubject' and @href=$review]]
     
     
     let $study-uri := $pdr-col/atom:link[@rel = "http://www.cggh.org/2010/chassis/terms/originStudy"]/@href
     let $study-path := substring-after( $study-uri , $config:self-link-uri-base )
     
     
     let $ret := 
         if (not(exists($pdr//atom:content//outcome)) or $pdr//atom:content//outcome = "fail" ) then
             let $study := atomdb:retrieve-member($study-path)
             return if ($study//app:draft = "no") then
                     let $new-entry := if (exists($pdr//atom:content)) then
                                         let $augmented := filter-plugin:get-pdr-augmentation($pdr//atom:content/review/outcome)
                                         let $new-entry := filter-plugin:augment-atom-entry($entry, $augmented)
                                         return $new-entry
                                        else
                                           $entry
                     
                     return $new-entry
                 else
                     ()
         else
             ()   
     return $ret
     else 
         $entry
};

declare function filter-plugin:do-filter-simple(
    $request as element(request) ,
	$entry as element()?
) as element()?
{
    
     let $param-filter := xutil:get-parameter( "filter" , $request )
     
     return if (contains($param-filter, "simple")) then
     let $message := ( "chassis-filter plugin, do-filter-simple: " ) 
	let $log := local:log4jDebug( $message )
         let $ret := 
        <atom:entry>
        {        
            $entry/attribute::* ,
            $entry/child::*[ not( . instance of element(atom:content) ) ],
            
            for $content in $entry/child::*[. instance of element(atom:content) ]
                let $simple := 
                <atom:content>
                {
                    $content/attribute::*
                }
                <study>
                {
                    $content/study/attribute::*,
                    $content/study/studyTitle,
                    $content/study/study-status,
                    $content/study/atombeat:group
                }
                </study>
                </atom:content>
                return $simple
        }
        </atom:entry>
    
          return $ret
     else 
         $entry
};

declare function filter-plugin:get-pdr-augmentation(
    $insert as element()
) as element(manta:review)
{
     let $ret := <manta:review>
        { $insert }
        </manta:review>
     
     return $ret
};

declare function filter-plugin:augment-atom-entry(
    $entry as element(atom:entry),
    $insert as element()
) as element(atom:entry)
{

    
    
    let $entry := 
        <atom:entry>
        {
            $entry/attribute::* ,
            $insert,
            for $child in $entry/child::* 
            return
                $child
            
        }
        </atom:entry>
            
    return $entry

};

declare function filter-plugin:replace-response-body( $response as element(response) , $body as item() ) as element(response)
{
    <response>
    {
        $response/status ,
        $response/headers
    }
        <body>{$body}</body>
    </response>
};
