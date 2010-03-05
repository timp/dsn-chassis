xquery version "1.0";

module namespace ah = "http://www.cggh.org/2010/xquery/history-protocol";

declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: see http://tools.ietf.org/html/draft-snell-atompub-revision-00 :)
declare namespace ar = "http://purl.org/atompub/revision/1.0" ;

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;
import module namespace v="http://exist-db.org/versioning" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace http = "http://www.cggh.org/2010/xquery/http" at "http.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/xquery/mime" at "mime-types.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace adb = "http://www.cggh.org/2010/xquery/atom-db" at "atom-db.xqm" ;
import module namespace ap = "http://www.cggh.org/2010/xquery/atom-protocol" at "atom-protocol.xqm" ;

declare variable $ah:param-name-revision-index as xs:string := "revision" ;


(:
 : TODO doc me
 :)
declare function ah:do-service()
as item()*
{

	let $request-path-info := request:get-attribute( $ap:param-request-path-info )
	let $request-method := request:get-method()
	
	return
	
		if ( $request-method = $http:method-get )
		
		then ah:do-get( $request-path-info )
		
		else ah:do-method-not-allowed( $request-path-info )

};




(:
 : TODO doc me 
 :)
declare function ah:do-get(
	$request-path-info as xs:string 
) as item()*
{

	if ( adb:member-available( $request-path-info ) )
	
	then ah:do-get-entry( $request-path-info )
	
	else ah:do-not-found( $request-path-info )
	
};




declare function ah:do-get-entry(
	$request-path-info as xs:string 
) as item()*
{

	let $log := util:log( "debug" , "== ah:do-get-entry() ==" )
	let $log := util:log( "debug" , $request-path-info )

    let $revision-index := request:get-parameter( $ah:param-name-revision-index , "" )
	let $log := util:log( "debug" , $revision-index )
	
	return
	
		if ( $revision-index = "" )
		
		then ah:do-get-entry-history( $request-path-info )
		
		else if ( $revision-index castable as xs:integer )
		
		then ah:do-get-entry-revision( $request-path-info , xs:integer( $revision-index ) )
		
		else ah:do-bad-request( $request-path-info , "Revision index parameter must be an integer." )
};




(:
 : TODO doc me
 :)
declare function ah:do-get-entry-history(
	$request-path-info as xs:string
) as element(atom:feed)
{
	let $log := util:log( "debug" , "== ah:do-get-entry-history() ==" )
	let $log := util:log( "debug" , $request-path-info )
	
    let $entry-doc-path := adb:request-path-info-to-db-path( $request-path-info )
	let $log := util:log( "debug" , $entry-doc-path )

    let $entry-doc := doc( $entry-doc-path )
    let $log := util:log( "debug" , $entry-doc )
    
    let $status-code := response:set-status-code( $http:status-success-ok )
    let $log := util:log( "debug" , concat( "status-code: " , $status-code ) )
    
    let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
    let $log := util:log( "debug" , concat( "header-content-type: " , $header-content-type ) )
    
    let $vhist := v:history( $entry-doc )
    let $log := util:log( "debug" , $vhist )
    
    let $vvers := v:versions( $entry-doc )
    let $log := util:log( "debug" , $vvers )
    
    let $revisions := v:revisions( $entry-doc )
    let $log := util:log( "debug" , $revisions )
    
    return 

		<atom:feed>
			<atom:title>History</atom:title>
			{

				$vhist , 
				
				$vvers , 
				
				for $i in 1 to ( count( $revisions ) + 1 )
				return ah:construct-entry-revision( $request-path-info , $entry-doc , $i , $revisions )
				
			}
		</atom:feed>

};




declare function ah:do-get-entry-revision(
	$request-path-info as xs:string ,
	$revision-index as xs:integer 
) as item()*
{

    let $entry-doc-path := adb:request-path-info-to-db-path( $request-path-info )

    let $entry-doc := doc( $entry-doc-path )

	(: 
	 : N.B. we need to map from sequential revision index per entry (e.g., 1, 2, 3)
	 : to eXist's revision numbers (e.g., 6, 21, 27) which are per collection.
	 :)
	
	(: 
	 : Also note that we will index the base revision at 1, but eXist only returns
	 : revisions subsequent to the base, so we will have to map revision index 2 to 
	 : the first eXist revision.
	 :)
	
    let $revision-numbers as xs:integer* := v:revisions( $entry-doc )

    return 
    
        if ( $revision-index <= 0 )
        
        then ah:do-bad-request( $request-path-info , "Revision index parameter must be an integer equal to or greater than 1." )
        
        else if ( $revision-index > ( count($revision-numbers) + 1 ) )
        
        then ah:do-not-found( $request-path-info )
        
        else 
        
    	    let $status-code := response:set-status-code( $http:status-success-ok )
    	    
    	    let $header-content-type := response:set-header( $http:header-content-type , $ap:atom-mimetype )
    
        	return ah:construct-entry-revision( $request-path-info , $entry-doc , $revision-index , $revision-numbers )
        
};



declare function ah:construct-entry-revision(
	$request-path-info as xs:string ,
	$entry-doc as node() ,
	$revision-index as xs:integer ,
	$revision-numbers as xs:integer*
) as element(atom:entry)
{
    
    (: 
     : Handle revision index 1 in a special way
     :)
     
    if ( $revision-index = 1 )
    
    then ah:construct-entry-base-revision( $request-path-info , $revision-numbers )
    
    else ah:construct-entry-specified-revision( $request-path-info , $entry-doc , $revision-index , $revision-numbers )
    
};




declare function ah:construct-entry-base-revision(
	$request-path-info as xs:string ,
	$revision-numbers as xs:integer*
) as element(atom:entry)
{

    let $log := util:log( "debug" , "== ah:construct-entry-base-revision() ==" )

    (: 
     : N.B. if no updates on the doc yet, then base revision won't have been
     : created by eXist versioning module, so we'll just grab the head.
     :)
    
    let $base-revision-db-path := 
        if ( empty( $revision-numbers) ) 
        then adb:request-path-info-to-db-path( $request-path-info )
        else concat( "/db/system/versions" , adb:request-path-info-to-db-path( $request-path-info ) , ".base" )
        
    let $log := util:log( "debug" , $base-revision-db-path )
    
    let $base-revision-doc := doc( $base-revision-db-path ) 
        
    let $log := util:log( "debug" , exists( $base-revision-doc ) )
    
    (: 
     : N.B. we need to copy the root element, perhaps because
     : documents in the /db/system/versions collection are not
     : indexed by qname?
     :)
     
    let $revision := utilx:copy( $base-revision-doc/* )
    
    let $when := $revision/atom:updated

	let $this-revision-href :=
		concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( 1 ) )	

	let $next-revision-href :=
		if ( count( $revision-numbers ) > 0 ) 
		then concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( 2 ) )	
		else ()

	let $current-revision-href :=
		concat( $config:service-url , $request-path-info )

	let $initial-revision-href := $this-revision-href

	(: N.B. don't need history link because already in entry :)
	
	return 
		<atom:entry>
			<ar:revision 
				number="1"
				when="{$when}"
				initial="yes">
			</ar:revision>
			<atom:link rel="current-revision" type="application/atom+xml" href="{$current-revision-href}"/>
			<atom:link rel="initial-revision" type="application/atom+xml" href="{$initial-revision-href}"/>
			<atom:link rel="this-revision" type="application/atom+xml" href="{$this-revision-href}"/>
		{
			if ( $next-revision-href ) then
			<atom:link rel="next-revision" type="application/atom+xml" href="{$next-revision-href}"/> 
			else () ,
			$revision/*
		}
		</atom:entry>
    
};





declare function ah:construct-entry-specified-revision(
	$request-path-info as xs:string ,
	$entry-doc as node() ,
	$revision-index as xs:integer ,
	$revision-numbers as xs:integer*
) as element(atom:entry)
{ 

    let $revision-number := $revision-numbers[$revision-index -1] 
    
	let $revision := v:doc( $entry-doc , $revision-number )

	let $when := $revision/atom:updated

	let $initial := "no"
	
	let $this-revision-href :=
		concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( $revision-index ) )	

	let $next-revision-href :=
		if ( $revision-index <= count( $revision-numbers ) ) 
		then concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( $revision-index + 1 ) )	
		else ()

	let $previous-revision-href :=
		if ( $revision-index > 1 ) 
		then concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( $revision-index - 1 ) )	
		else ()
		
	let $current-revision-href :=
		concat( $config:service-url , $request-path-info )

	let $initial-revision-href :=
		concat( $config:history-service-url , $request-path-info , "?" , $ah:param-name-revision-index , "=" , xs:string( 1 ) )	

	(: N.B. don't need history link because already in entry :)
	
	return 
		<atom:entry>
			<ar:revision 
				number="{$revision-index}"
				when="{$when}"
				initial="{$initial}">
			</ar:revision>
			<atom:link rel="current-revision" type="application/atom+xml" href="{$current-revision-href}"/>
			<atom:link rel="initial-revision" type="application/atom+xml" href="{$initial-revision-href}"/>
			<atom:link rel="this-revision" type="application/atom+xml" href="{$this-revision-href}"/>
		{
			if ( $next-revision-href ) then
			<atom:link rel="next-revision" type="application/atom+xml" href="{$next-revision-href}"/> 
			else () ,
			if ( $previous-revision-href ) then
			<atom:link rel="previous-revision" type="application/atom+xml" href="{$previous-revision-href}"/> 
			else () ,
			$revision/*
		}
		</atom:entry>
};





declare function ah:do-not-found(
	$request-path-info
) as item()?
{

    let $status-code := response:set-status-code( $http:status-client-error-not-found )
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )
	let $response := 
	
		<response>
			<message>The server has not found anything matching the Request-URI.</message>
			<path-info>{$request-path-info}</path-info>
			<service-url>{$config:service-url}</service-url>
		</response>

	return $response
		
};



declare function ah:do-bad-request(
	$request-path-info as xs:string ,
	$message as xs:string 
) as item()?
{

	let $request-data := request:get-data()
	
    let $status-code := response:set-status-code( $http:status-client-error-bad-request )
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )
	let $response := 
	
		<response>
			<message>{$message} The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.</message>
			<service-url>{$config:service-url}</service-url>
			<method>{request:get-method()}</method>
			<path-info>{$request-path-info}</path-info>
			<headers>
			{
				for $header-name in request:get-header-names()
				return
					element  { lower-case( $header-name ) } 
					{
						request:get-header( $header-name )						
					}
			}
			</headers>
			<data>{$request-data}</data>
		</response>
			
	return $response
    
};




declare function ah:do-method-not-allowed(
	$request-path-info
) as item()?
{

	let $request-data := request:get-data()

	let $status-code := response:set-status-code( $http:status-client-error-method-not-allowed )

	let $header-allow := response:set-header( $http:header-allow , "GET POST PUT" )

	(: 
	 : TODO DELETE method and different allows depending on resource identified 
	 :)
	
	let $header-content-type := response:set-header( $http:header-content-type , "application/xml" )

	let $response := 
	
		<response>
			<message>The method specified in the Request-Line is not allowed for the resource identified by the Request-URI.</message>
			<service-url>{$config:service-url}</service-url>
			<method>{request:get-method()}</method>
			<path-info>{$request-path-info}</path-info>
			<headers>
			{
				for $header-name in request:get-header-names()
				return
					element  { lower-case( $header-name ) } 
					{
						request:get-header( $header-name )						
					}
			}
			</headers>
			<data>{$request-data}</data>
		</response>
			
	return $response

};