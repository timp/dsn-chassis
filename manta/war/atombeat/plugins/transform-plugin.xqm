xquery version "1.0";

module namespace transform-plugin = "http://www.cggh.org/2010/chassis/transform/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace transform-plugin = "http://www.cggh.org/2010/chassis/transform/xquery/atombeat-plugin" ;


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


declare variable $transform-plugin:reserved :=
    <reserved>
        <elements namespace-uri="http://www.cggh.org/2010/chassis/transform/xmlns">
            <element>id</element>
        </elements>
        <atom-links>
            <link rel="http://www.cggh.org/2010/chassis/terms/transform/flatten"/>
        </atom-links>
    </reserved>
;

declare variable $transform-plugin:logger-name := "org.cggh.chassis.transform.xquery.atombeat.plugin";


declare function local:log4jDebug(
    $message as item()*
) as empty()
{
  util:log-app( "debug" , $transform-plugin:logger-name , $message ) (: only use within our function :)
};

declare function local:log4jInfo(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $transform-plugin:logger-name , $message ) (: only use within our function :)
};

declare function transform-plugin:before(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$request-data as item()* ,
	$request-media-type as xs:string?
) as item()*
{
	
	let $message := ( "transform plugin, before: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return 
	
	    if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
	    then transform-plugin:filter-rel( $request-data )
	    else $request-data

};



declare function transform-plugin:after(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{

	let $message := ( "transform plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return
		
		if ( $operation = $CONSTANT:OP-CREATE-MEMBER )
		
		then transform-plugin:after-retrieve-member( $request-path-info , $response )
		
		else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
		
		then transform-plugin:after-retrieve-member( $request-path-info , $response )
		else if ( $operation = $CONSTANT:OP-RETRIEVE-MEMBER )
		
		then transform-plugin:after-retrieve-member( $request-path-info , $response )
		else $response

};
declare function transform-plugin:after-retrieve-member(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
     if ( matches( $request-path-info , "^/study-info/[^/]+.atom" ) )
    
    then transform-plugin:after-retrieve-member-study-info( $response )
    else $response

};

declare function transform-plugin:do-transform(
    $response as element(response),
    $content-type as xs:string,
    $transform-type as xs:string
) as element(response)
{
  let $request-path-info := request:get-attribute( "request-path-info" )
    let $message := ( "transform plugin:  request-path-info: " , $request-path-info ) 
    let $log := local:log4jDebug( $message )
    let $groups := text:groups( $request-path-info , "^/([^/]+)/(.*)$" )
    let $data-type := $groups[2]
 
 	let $transform := concat($config:service-url-base,"/../xslt/",$transform-type,"-",$data-type,".xsl")
	
	let $message := ( "transform plugin, do-transform: " , $transform) 
	let $log := local:log4jDebug( $message )
    let $entry := transform:transform($response/body, xs:anyURI($transform), ())
    let $return := 
 <response>
    {
        $response/status
	}
	<headers> {
	$response/headers/header[not(name/text()=$CONSTANT:HEADER-CONTENT-TYPE)],
		<header>
			<name>{$CONSTANT:HEADER-CONTENT-TYPE}:</name>
			<value>{$content-type}</value>
		</header>
		}
	</headers>
<body type="text">
	{$entry}
</body>
</response>
    
    let $message := ( "transform plugin, after-retrieve-study-info returns: ", $return ) 
	let $log := local:log4jDebug( $message )
	
    return $return

};
declare function transform-plugin:after-retrieve-member-study-info(
	$response as element(response)
) as element(response)
{
    let $transform-type := request:get-parameter("transform", "none")
    let $content-type := $CONSTANT:MEDIA-TYPE-CSV
    
    return if ($transform-type = "none") then
        let $entry := $response/body/atom:entry
        let $rel := 'http://www.cggh.org/2010/chassis/terms/transform/flatten'        
        let $tt := 'flatten' 
        let $new-entry := transform-plugin:augment-entry($entry, $rel, $content-type, $tt)
        return transform-plugin:replace-response-body($response, $new-entry) 
    else
        transform-plugin:do-transform($response, $content-type, $transform-type)
            
};

(: Remove relevant rel otherwise another will be added each time a put is done :)
declare function transform-plugin:filter-rel(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $entry , $transform-plugin:reserved )
    
    return $filtered-entry
    
};

declare function transform-plugin:augment-entry(
    $entry as element(atom:entry),
    $rel as xs:string,
    $content-type as xs:string,
    $transform-type as xs:string
) as element(atom:entry)
{
    <atom:entry>
    {
        $entry/attribute::* ,
        $entry/child::*,
        <atom:link rel="{$rel}" type="{$content-type}" href="{($entry/atom:link[@rel='edit'])/@href}?transform={$transform-type}"/>
    }        
    </atom:entry>
};

declare function transform-plugin:replace-response-body( $response as element(response) , $body as item() ) as element(response)
{
    <response>
    {
        $response/status ,
        $response/headers
    }
        <body>{$body}</body>
    </response>
};
