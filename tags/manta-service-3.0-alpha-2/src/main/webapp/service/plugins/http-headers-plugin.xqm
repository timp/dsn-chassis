xquery version "1.0";

module namespace http-headers-plugin = "http://www.cggh.org/2010/chassis/http-headers/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace http-headers = "http://www.cggh.org/2010/chassis/http-headers/xmlns" ;


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


declare variable $http-headers-plugin:reserved :=
    <reserved>
        <elements namespace-uri="http://www.cggh.org/2010/chassis/http-headers/xmlns">
            <element>id</element>
        </elements>
        <atom-links>
            <link rel="http://www.cggh.org/2010/chassis/terms/submittedMedia"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/curatedMedia"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/derivations"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/studyInfo"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/originStudy"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/derivation"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/derived"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/draftMedia"/>
        </atom-links>
    </reserved>
;

declare variable $http-headers-plugin:logger-name := "org.cggh.chassis.http-headers.xquery.atombeat.plugin";


declare function local:log4jDebug(
    $message as item()*
) as empty()
{
  util:log-app( "debug" , $http-headers-plugin:logger-name , $message ) (: only use within our function :)
};

declare function local:log4jInfo(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $http-headers-plugin:logger-name , $message ) (: only use within our function :)
};

declare function http-headers-plugin:get-request-path-info($request as element(request) ) as xs:string
{
    let $request-path-info := $request/path-info/text()
    let $log := local:log4jDebug($request-path-info)
    return $request-path-info
};

declare function http-headers-plugin:after(
    $operation as xs:string ,
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
 
    let $request-path-info := http-headers-plugin:get-request-path-info($request)
	let $message := ( "chassis-http-headers plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return
		
	if ( $operation = $CONSTANT:OP-RETRIEVE-MEDIA )
		
		then http-headers-plugin:after-retrieve-media( $request-path-info , $response )
		
	else $response

};


(: Modify cache settings to work around problem in IE :)
declare function http-headers-plugin:after-retrieve-media(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
<response>
    {
        $response/status
	}
	<headers> {
		$response/headers/header
	}
		<header>
			<name>Cache-Control</name>
			<value>private</value>
		</header>
		<header>
			<name>Pragma</name>
			<value>private</value>
		</header>
	</headers>
	{$response/body}
</response>
};
