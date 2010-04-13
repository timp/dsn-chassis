xquery version "1.0";

module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace manta = "http://www.cggh.org/2010/chassis/manta/xmlns" ;


import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace CONSTANT = "http://www.cggh.org/2010/atombeat/xquery/constants" at "../lib/constants.xqm" ;

import module namespace config = "http://www.cggh.org/2010/atombeat/xquery/config" at "../config/shared.xqm" ;

import module namespace xutil = "http://www.cggh.org/2010/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace mime = "http://www.cggh.org/2010/atombeat/xquery/mime" at "../lib/mime.xqm" ;
import module namespace atomdb = "http://www.cggh.org/2010/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace atomsec = "http://www.cggh.org/2010/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;




declare function local:debug(
    $message as item()*
) as empty()
{
    util:log-app( "debug" , "org.cggh.chassis.manta.xquery.atombeat.plugin" , $message )
};



(: TODO test filter out submittedMedia links on create or update study member :)

(: TODO add originStudy links to submitted media entries :)

declare function manta-plugin:before(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$request-data as item()* ,
	$request-media-type as xs:string?
) as item()*
{
	
	let $message := ( "chassis-manta plugin, before: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:debug( $message )

	return 
	
	    if ( $operation = $CONSTANT:OP-CREATE-MEMBER )
	    then manta-plugin:before-create-member( $request-path-info , $request-data )
	    
	    else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
	    then manta-plugin:before-update-member( $request-path-info , $request-data )
	    
	    else
        	let $status-code := 0 (: we don't want to interrupt request processing :)
	        return ( $status-code , $request-data )

};




declare function manta-plugin:before-create-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) 
) as item()*
{
    if ( $request-path-info = "/studies" ) 
    then manta-plugin:before-create-member-study( $request-data )
    else
        let $status-code := 0 (: we don't want to interrupt request processing :)
        return ( $status-code , $request-data )
};




declare function manta-plugin:before-update-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) 
) as item()*
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-study( $request-path-info , $request-data )
    else
        let $status-code := 0 (: we don't want to interrupt request processing :)
        return ( $status-code , $request-data )
};




declare function manta-plugin:before-create-member-study(
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-study-entry( $request-data )
    let $status-code := 0 (: we don't want to interrupt request processing :)
    return ( $status-code , $request-data )
};



declare function manta-plugin:before-update-member-study(
	$request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-study-entry( $request-data )
    let $status-code := 0 (: we don't want to interrupt request processing :)
    return ( $status-code , $request-data )
};




declare function manta-plugin:filter-study-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{
    <atom:entry>
    {
        $entry/attribute::* ,
        for $child in $entry/child::*
        where ( 
            not( 
                local-name( $child ) = $CONSTANT:ATOM-LINK 
                and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                and $child/@rel = 'http://www.cggh.org/2010/chassis/terms/submittedMedia'
            )
            and not(
                local-name( $child ) = "id" 
                and namespace-uri( $child ) = "http://www.cggh.org/2010/chassis/manta/xmlns"
            )
        )
        return $child
    }
    </atom:entry>
};




declare function manta-plugin:after(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$response-data as item()* ,
	$response-content-type as xs:string?
) as item()*
{

	let $message := ( "chassis-manta plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:debug( $message )

	return
		
		if ( $operation = $CONSTANT:OP-CREATE-MEMBER ) 
		
		then manta-plugin:after-create-member( $request-path-info , $response-data , $response-content-type )
		
		else if ( $operation = $CONSTANT:OP-RETRIEVE-MEMBER )
		
		then manta-plugin:after-retrieve-member( $request-path-info , $response-data , $response-content-type )
		
		else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
		
		then manta-plugin:after-update-member( $request-path-info , $response-data , $response-content-type )
		
		else if ( $operation = $CONSTANT:OP-LIST-COLLECTION )
		
		then manta-plugin:after-list-collection( $request-path-info , $response-data , $response-content-type )
		
		else if ( $operation = $CONSTANT:OP-CREATE-MEDIA )
		
		then manta-plugin:after-create-media( $request-path-info , $response-data , $response-content-type )
		
		else
		
    		(: pass response data and content type through, we don't want to modify response :)
    		( $response-data , $response-content-type )

};




declare function manta-plugin:after-create-member(
	$request-path-info as xs:string ,
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-create-member-studies( $entry , $response-content-type )
    
    else if ( matches( $request-path-info , "^/studies/[^/]+/media" ) )
    
    then manta-plugin:after-create-member-media( $entry , $response-content-type )
    
    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $entry , $response-content-type )

};



declare function manta-plugin:after-create-media(
	$request-path-info as xs:string ,
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    if ( matches( $request-path-info , "^/studies/[^/]+/media" ) )
    
    then manta-plugin:after-create-media-media( $entry , $response-content-type )
    
    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $entry , $response-content-type )

};



declare function manta-plugin:after-retrieve-member(
	$request-path-info as xs:string ,
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    
    then manta-plugin:after-retrieve-member-studies( $entry , $response-content-type )
    
    else if ( matches( $request-path-info , "^/studies/[^/]+/media" ) )
    
    then manta-plugin:after-retrieve-member-media( $entry , $response-content-type )
    
    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $entry , $response-content-type )

};



declare function manta-plugin:after-update-member(
	$request-path-info as xs:string ,
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    
    then manta-plugin:after-update-member-studies( $entry , $response-content-type )
    
    else if ( matches( $request-path-info , "^/studies/[^/]+/media" ) )
    
    then manta-plugin:after-update-member-media( $entry , $response-content-type )
    
    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $entry , $response-content-type )

};



declare function manta-plugin:after-list-collection(
	$request-path-info as xs:string ,
	$feed as element(atom:feed) ,
	$response-content-type as xs:string?
) as item()*
{
    if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-list-collection-studies( $feed , $response-content-type )
    
    else if ( matches( $request-path-info , "^/studies/[^/]+/media" ) )
    
    then manta-plugin:after-list-collection-media( $feed , $response-content-type )
    
    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $feed , $response-content-type )

};



declare function manta-plugin:after-create-member-studies(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    (: 
     : We want to create a dedicated collection where study members can 
     : submit media for the study.
     :)
     
    let $log := local:debug( "== manta-plugin:after-create-member-studies() ==")
    
    let $id := manta-plugin:get-id( $entry )
    
    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $log := local:debug( $submitted-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Submitted Media for Study {$id} ({$entry/atom:title/text()})</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$entry/atom:link[@rel='self']/@href}" 
                type="application/atom+xml"/>
        </atom:feed>
        
    let $submitted-media-collection-db-path := atomdb:create-collection( $submitted-media-collection-path-info , $feed )
    
    let $entry-path-info := substring-after( $entry/atom:link[@rel="self"]/@href , $config:service-url )
    
    let $submitted-media-collection-acl :=
        <acl>
            <groups>
                <group name="owners" src="{$entry-path-info}"/>
                <group name="submitters" src="{$entry-path-info}"/>
            </groups>
            <rules>
                <!-- owners -->
                <allow>
                    <group>owners</group>
                    <operation>list-collection</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>create-media</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>retrieve-member</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>retrieve-media</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>update-member</operation>
                </allow>
                <allow>
                    <group>owners</group>
                    <operation>update-acl</operation>
                </allow>
                <!-- submitters -->
                <allow>
                    <group>submitters</group>
                    <operation>list-collection</operation>
                </allow>
                <allow>
                    <group>submitters</group>
                    <operation>create-media</operation>
                </allow>
                <allow>
                    <group>submitters</group>
                    <operation>retrieve-member</operation>
                </allow>
                <allow>
                    <group>submitters</group>
                    <operation>retrieve-media</operation>
                </allow>
                <allow>
                    <group>submitters</group>
                    <operation>update-member</operation>
                </allow>
            </rules>
        </acl>
    
    let $acl-stored := atomsec:store-collection-acl( $submitted-media-collection-path-info , $submitted-media-collection-acl )
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-create-member-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    let $entry := manta-plugin:augment-media-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};



declare function manta-plugin:after-create-media-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    let $entry := 
        if ( starts-with( $response-content-type , $CONSTANT:MEDIA-TYPE-ATOM ) )
        then manta-plugin:augment-media-entry( $entry )
        else $entry

    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-retrieve-member-studies(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-retrieve-member-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-media-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-update-member-studies(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-update-member-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-media-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-list-collection-studies(
	$feed as element(atom:feed) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $feed := 
        <atom:feed>
        {
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            for $entry in $feed/atom:entry
            return manta-plugin:augment-study-entry( $entry )
        }
        </atom:feed>
    
    return ( $feed , $response-content-type )
    
};




declare function manta-plugin:after-list-collection-media(
	$feed as element(atom:feed) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $feed := 
        <atom:feed>
        {
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            for $entry in $feed/atom:entry
            return manta-plugin:augment-media-entry( $entry )
        }
        </atom:feed>
    
    return ( $feed , $response-content-type )
    
};




declare function manta-plugin:submitted-media-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $submitted-media-collection-path-info := concat( "/studies/" , $id , "/media/submitted" )
    return $submitted-media-collection-path-info
};




declare function manta-plugin:get-id(
    $entry as element(atom:entry)
) as xs:string
{
    replace( $entry/atom:id/text() , '^.*/([^/^.]+)\.atom$', '$1' )
};



declare function manta-plugin:augment-study-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $submitted-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/submittedMedia" 
            href="{concat( $config:service-url , $submitted-media-collection-path-info )}"
            type="application/atom+xml"/>
        
    let $entry := 
        <atom:entry>
            <manta:id>{manta-plugin:get-id($entry)}</manta:id>
        {
            $entry/attribute::* ,
            $entry/child::* ,
            $submitted-media-collection-link
        }
        </atom:entry>
            
    return $entry

};



declare function manta-plugin:augment-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $entry := 
        <atom:entry>
            <manta:id>{manta-plugin:get-id($entry)}</manta:id>
        {
            $entry/attribute::* ,
            $entry/child::* 
        }
        </atom:entry>
            
    return $entry

};