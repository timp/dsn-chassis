xquery version "1.0";

module namespace tombstone-plugin = "http://purl.org/atombeat/xquery/tombstone-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace tombstone = "http://purl.org/atombeat/xquery/tombstone/xmlns" ;

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

declare variable $tombstone-plugin:logger-name := "org.purl.atombeat.plugin.tombstone-plugin";


declare function local:log4jDebug(
    $message as item()*
) as empty()
{
  util:log-app( "debug" , $tombstone-plugin:logger-name , $message ) (: only use within our function :)
};

declare function local:log4jInfo(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $tombstone-plugin:logger-name , $message ) (: only use within our function :)
};

declare function tombstone-plugin:before(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$request-data as item()* ,
	$request-media-type as xs:string?
) as item()*
{
	
	let $message := ( "before: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return 
	
	    if ( $operation = $CONSTANT:OP-DELETE-MEDIA )
	    then 
	        let $process := tombstone-plugin:before-delete-media( $request-path-info )
	        return $request-data
        else if ( $operation = $CONSTANT:OP-DELETE-MEMBER )
        then 
            let $process := tombstone-plugin:before-delete-member( $request-path-info )
            return $request-data
	    else $request-data

};

declare function tombstone-plugin:before-delete-media(
	$request-path-info as xs:string  
) 
{
    if ( matches($request-path-info, "media$" )) 
    then 
        let $path-info := replace( $request-path-info , "media$" , "atom" )
        return tombstone-plugin:before-delete-member-submitted-member( $path-info )
    else if ( matches($request-path-info, "atom$" )) 
    then 
        let $path-info := replace( $request-path-info , "media$" , "atom" )
        return tombstone-plugin:before-delete-member-submitted-member( $request-path-info )
    else ()
};

declare function tombstone-plugin:before-delete-member(
	$request-path-info as xs:string  
) 
{
    if ( matches($request-path-info, "^/media/submitted" )) 
    then tombstone-plugin:before-delete-member-submitted-member( $request-path-info )
    else ()
};

declare function tombstone-plugin:before-delete-member-submitted-member(
	$request-path-info as xs:string  
) 
{

  let $entry := if ( atomdb:member-available( $request-path-info ) )
     then
         let $raw-entry := atomdb:retrieve-member( $request-path-info )
         return $raw-entry
     else ()
     let $title := xs:string($entry//atom:title/text())
  let $set := request:set-attribute('filename1', $title)
  
  return $entry
};

declare function tombstone-plugin:after(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{

	let $message := ( "after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return
		
	if ($operation = $CONSTANT:OP-DELETE-MEMBER)
		then 
		    let $op := tombstone-plugin:create-tombstone( $request-path-info , $response )
		    return $op
    else if ( $operation = $CONSTANT:OP-DELETE-MEDIA)
		then
		    let $path-info := replace( $request-path-info , "media$" , "atom" )
		    let $op := tombstone-plugin:create-tombstone( $path-info , $response )
		    return $op	
	else if ( $operation = $CONSTANT:OP-LIST-COLLECTION )
		
		then tombstone-plugin:add-tombstones( $request-path-info , $response )
	else if ( $operation = $CONSTANT:OP-RETRIEVE-MEMBER )
	    then tombstone-plugin:after-retrieve-member($response)
	else $response

};

declare function tombstone-plugin:add-tombstones($request-path-info as xs:string, $response as element(response)) as element(response)
{
    if ( matches( $request-path-info , "\.atom$" ) )
    then tombstone-plugin:add-submitted-media-tombstones( $request-path-info, $response )
    else $response
};

declare function tombstone-plugin:add-submitted-media-tombstones(
    $request-path-info as xs:string,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $entries := xutil:get-entries($feed)
    let $all := $entries
    (:
    let $tombstones := tombstone-db:retrieve-members($request-path-info, false())
    let $all := ($entries, $tombstones)
    :)
    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not((. instance of element(atom:entry) ) or (. instance of element(at:deleted-entry)))] ,
            
            for $entry in $all
                return $entry
        }
        </atom:feed>
    
    return tombstone-plugin:replace-response-body( $response , $feed )
    
};
declare function tombstone-plugin:is-tombstone-enabled(
    $collection-db-path as xs:string 
) as xs:boolean 
{       
    let $feed := atomdb:retrieve-feed-without-entries( $collection-db-path )
 
    let $enabled := if ($feed[exists(@atombeat:enable-tombstones)] ) then
          xs:boolean($feed/@atombeat:enable-tombstones)
      else
          false()

    return $enabled
    
};


declare function tombstone-plugin:create-tombstone($request-path-info as xs:string, $response as element(response)) as element(response)
{
   let $tombstone := tombstone-plugin:create-tombstone-entry($request-path-info, $response)
    let $groups := text:groups( $request-path-info , "^(.*)/([^/]+)$" )
    let $collection := $groups[2]
    let $resource := $groups[3]
    let $enable-tombstones := tombstone-plugin:is-tombstone-enabled($collection)
    let $store := if ($enable-tombstones) then
        atomdb:store-member($collection,$resource,$tombstone)
       else
       ()
    return tombstone-plugin:replace-response-body( $response , $tombstone)
};
declare function tombstone-plugin:create-tombstone-entry($request-path-info as xs:string, $response as element(response)) as element(response)
{
  let $published := current-dateTime()
  let $ref := concat($config:content-service-url,$request-path-info)
  (: TODO review this, maybe provide user as function arg, rather than interrogate request here :)
  let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
  let $filename := request:get-attribute ( 'filename1')
  
  return
        <at:deleted-entry
          ref="{$ref}"
          when="{$published}">
          { 
            if ( $config:auto-author )
            then
                <at:by>
                {
                    if ( $config:user-name-is-email ) then <atom:email>{$user-name}</atom:email>
                    else <atom:name>{$user-name}</atom:name>
                }                
               </at:by>
            else ()
        }
          <at:comment>{$filename}</at:comment>
          <atom:link rel="self" type="application/atomdeleted+xml;charset=UTF-8" href="{$ref}"/>
        </at:deleted-entry>

};

declare function tombstone-plugin:after-retrieve-member( $response as element(response) ) as element(response) {

    let $ret := if (not($response/body/child::* instance of element(at:deleted-entry))) then
        let $out := $response
        return $out
      else
          let $out := (
              <response>
                 <status>410</status>
                 <headers> {
	$response/headers/header[not(name/text()=$CONSTANT:HEADER-CONTENT-TYPE)],
		<header>
			<name>{$CONSTANT:HEADER-CONTENT-TYPE}</name>
			<value>application/atomdeleted+xml;charset=UTF-8</value>
		</header>
		         }
	             </headers>
                 { 
                     $response/body
                 }
              </response>
          )
          return $out

    return $ret        
};

declare function tombstone-plugin:replace-response-body( $response as element(response) , $body as item() ) as element(response)
{
    <response>
    {
        $response/status ,
        $response/headers
    }
        <body>{$body}</body>
    </response>
};
