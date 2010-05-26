xquery version "1.0";

module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace manta = "http://www.cggh.org/2010/chassis/manta/xmlns" ;


import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;

import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace mime = "http://purl.org/atombeat/xquery/mime" at "../lib/mime.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;




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
    else if ( matches( $request-path-info , "/media/submitted/[^/]+/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-submitted-media( $request-path-info , $request-data )
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



declare function manta-plugin:before-update-member-submitted-media(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-media-entry( $request-data )
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
                local-name( $child ) = $CONSTANT:ATOM-LINK 
                and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                and $child/@rel = 'http://www.cggh.org/2010/chassis/terms/curatedMedia'
            )
            and not( 
                local-name( $child ) = $CONSTANT:ATOM-LINK 
                and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                and $child/@rel = 'http://www.cggh.org/2010/chassis/terms/derivations'
            )
            and not( 
                local-name( $child ) = $CONSTANT:ATOM-LINK 
                and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                and $child/@rel = 'http://www.cggh.org/2010/chassis/terms/personalDataReviews'
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




declare function manta-plugin:filter-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{
    <atom:entry>
    {
        $entry/attribute::* ,
        for $child in $entry/child::*
        where ( 
            not(
                local-name( $child ) = "id" 
                and namespace-uri( $child ) = "http://www.cggh.org/2010/chassis/manta/xmlns"
            )
            and not( 
                local-name( $child ) = $CONSTANT:ATOM-LINK 
                and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                and $child/@rel = 'http://www.cggh.org/2010/chassis/terms/originStudy'
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




(: TODO split media into submitted and curated, and add derivations collection :)


declare function manta-plugin:after-create-member(
	$request-path-info as xs:string ,
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-create-member-studies( $entry , $response-content-type )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-member-submitted-media( $entry , $response-content-type )
    
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

    if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-media-submitted-media( $entry , $response-content-type )
    
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
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-retrieve-member-submitted-media( $entry , $response-content-type )
    
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
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-update-member-submitted-media( $entry , $response-content-type )
    
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
    
    else if ( matches( $request-path-info , "^/media" ) )
    
    then manta-plugin:after-list-collection-media( $feed , $response-content-type )
    
    else if ( matches( $request-path-info , "^/reviews/personal-data/[^/]+" ) )
    
    then manta-plugin:after-list-collection-personal-data-reviews( $request-path-info , $feed , $response-content-type )

    else

    	(: pass response data and content type through, we don't want to modify response :)
    	( $feed , $response-content-type )

};



declare function manta-plugin:after-create-member-studies(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    let $log := local:debug( "== manta-plugin:after-create-member-studies() ==")
    
    let $id := manta-plugin:get-id( $entry )
    
    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $log := local:debug( $submitted-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Submitted Media for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$entry/atom:link[@rel='self']/@href}" 
                type="application/atom+xml"/>
        </atom:feed>
        
    let $submitted-media-collection-db-path := atomdb:create-collection( $submitted-media-collection-path-info , $feed )
    let $submitted-media-collection-descriptor := config:submitted-media-collection-security-descriptor( $submitted-media-collection-path-info )
    let $descriptor-stored := atomsec:store-collection-descriptor( $submitted-media-collection-path-info , $submitted-media-collection-descriptor )
    
    let $curated-media-collection-path-info := manta-plugin:curated-media-collection-path-info-for-study-entry( $entry )
    let $log := local:debug( $curated-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Curated Media for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$entry/atom:link[@rel='self']/@href}" 
                type="application/atom+xml"/>
        </atom:feed>
        
    let $curated-media-collection-db-path := atomdb:create-collection( $curated-media-collection-path-info , $feed )
    let $curated-media-collection-descriptor := config:curated-media-collection-security-descriptor( $curated-media-collection-path-info )
    let $descriptor-stored := atomsec:store-collection-descriptor( $curated-media-collection-path-info , $curated-media-collection-descriptor )
    
    let $derivations-collection-path-info := manta-plugin:derivations-collection-path-info-for-study-entry( $entry )
    let $log := local:debug( $derivations-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Derivations for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$entry/atom:link[@rel='self']/@href}" 
                type="application/atom+xml"/>
        </atom:feed>
        
    let $derivations-collection-db-path := atomdb:create-collection( $derivations-collection-path-info , $feed )
    let $derivations-collection-descriptor := config:derivations-collection-security-descriptor( $derivations-collection-path-info )
    let $descriptor-stored := atomsec:store-collection-descriptor( $derivations-collection-path-info , $derivations-collection-descriptor )
    
    let $personal-data-reviews-collection-path-info := manta-plugin:personal-data-reviews-collection-path-info-for-study-entry( $entry )
    let $log := local:debug( $personal-data-reviews-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Personal Data Reviews for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$entry/atom:link[@rel='self']/@href}" 
                type="application/atom+xml"/>
        </atom:feed>
        
    let $personal-data-reviews-collection-db-path := atomdb:create-collection( $personal-data-reviews-collection-path-info , $feed )
    let $personal-data-reviews-collection-descriptor := config:personal-data-reviews-collection-security-descriptor( $personal-data-reviews-collection-path-info )
    let $descriptor-stored := atomsec:store-collection-descriptor( $personal-data-reviews-collection-path-info , $personal-data-reviews-collection-descriptor )
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};




declare function manta-plugin:after-create-member-submitted-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    let $entry := manta-plugin:augment-submitted-media-entry( $entry )
    
    return ( $entry , $response-content-type )
    
};



declare function manta-plugin:after-create-media-submitted-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{

    let $entry := 
        if ( starts-with( $response-content-type , $CONSTANT:MEDIA-TYPE-ATOM ) )
        then manta-plugin:augment-submitted-media-entry( $entry )
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




declare function manta-plugin:after-retrieve-member-submitted-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-submitted-media-entry( $entry )
    
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




declare function manta-plugin:after-update-member-submitted-media(
	$entry as element(atom:entry) ,
	$response-content-type as xs:string?
) as item()*
{
    
    let $entry := manta-plugin:augment-submitted-media-entry( $entry )
    
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
            return manta-plugin:augment-submitted-media-entry( $entry )
            
        }
        </atom:feed>
    
    return ( $feed , $response-content-type )
    
};




declare function manta-plugin:after-list-collection-personal-data-reviews(
    $request-path-info as xs:string ,
    $feed as element(atom:feed) ,
    $response-content-type as xs:string?
) as item()*
{
    
    let $param-review-subject := request:get-parameter( "reviewSubject" , "" )
    
    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            
            for $entry in $feed/atom:entry
            return 
                if ( 
                    $param-review-subject = "" 
                    or $param-review-subject = $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/reviewSubject"]/@href
                ) 
                then $entry
                else ()
            
        }
        </atom:feed>
    
    return ( $feed , $response-content-type )
    
};




declare function manta-plugin:submitted-media-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $collection-path-info := concat( "/media/submitted/" , $id )
    return $collection-path-info
};




declare function manta-plugin:curated-media-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $collection-path-info := concat( "/media/curated/" , $id )
    return $collection-path-info
};



declare function manta-plugin:derivations-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $collection-path-info := concat( "/derivations/" , $id )
    return $collection-path-info
};



declare function manta-plugin:personal-data-reviews-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    return manta-plugin:personal-data-reviews-collection-path-info( $id )
};




declare function manta-plugin:personal-data-reviews-collection-path-info(
    $id as xs:string
) as xs:string
{
    let $collection-path-info := concat( "/reviews/personal-data/" , $id )
    return $collection-path-info
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
        
    let $curated-media-collection-path-info := manta-plugin:curated-media-collection-path-info-for-study-entry( $entry )
    let $curated-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/curatedMedia" 
            href="{concat( $config:service-url , $curated-media-collection-path-info )}"
            type="application/atom+xml"/>
        
    let $derivations-collection-path-info := manta-plugin:derivations-collection-path-info-for-study-entry( $entry )
    let $derivations-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/derivations" 
            href="{concat( $config:service-url , $derivations-collection-path-info )}"
            type="application/atom+xml"/>
        
    let $personal-data-reviews-collection-path-info := manta-plugin:personal-data-reviews-collection-path-info-for-study-entry( $entry )
    let $personal-data-reviews-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" 
            href="{concat( $config:service-url , $personal-data-reviews-collection-path-info )}"
            type="application/atom+xml"/>
        
    let $entry := 
        <atom:entry>
            <manta:id>{manta-plugin:get-id($entry)}</manta:id>
        {
            $entry/attribute::* ,
            $entry/child::* ,
            $submitted-media-collection-link ,
            $curated-media-collection-link ,
            $derivations-collection-link ,
            $personal-data-reviews-collection-link
        }
        </atom:entry>
            
    return $entry

};



declare function manta-plugin:augment-submitted-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $id := manta-plugin:get-id($entry)
    
    let $study-id := text:groups( $entry/atom:link[@rel='self']/@href , "([^/]+)/[^/]+\.atom$" )[2]
    let $origin-study-path-info := concat( "/studies/" , $study-id , ".atom" )
    let $origin-study-uri := concat( $config:service-url , $origin-study-path-info )
    
    let $personal-data-reviews-uri := 
        concat( 
            $config:service-url , 
            manta-plugin:personal-data-reviews-collection-path-info( $study-id ) ,
            "?reviewSubject=" ,
            $entry/atom:link[@rel='self']/@href
        )
    
    let $entry := 
        <atom:entry>
            <manta:id>{$id}</manta:id>
        {
            $entry/attribute::* ,
            $entry/child::* ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="{$origin-study-uri}" type="application/atom+xml"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" href="{$personal-data-reviews-uri}" type="application/atom+xml"/>
        }
        </atom:entry>
            
    return $entry

};




