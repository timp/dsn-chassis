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
import module namespace security-config = "http://purl.org/atombeat/xquery/security-config" at "../config/security.xqm" ;

import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace mime = "http://purl.org/atombeat/xquery/mime" at "../lib/mime.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;


declare variable $manta-plugin:reserved :=
    <reserved>
        <elements namespace-uri="http://www.cggh.org/2010/chassis/manta/xmlns">
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

declare variable $manta-plugin:logger-name := "org.cggh.chassis.manta.xquery.atombeat.plugin";


declare function local:log4jDebug(
    $message as item()*
) as empty()
{
  util:log-app( "debug" , $manta-plugin:logger-name , $message ) (: only use within our function :)
};

declare function local:log4jInfo(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $manta-plugin:logger-name , $message ) (: only use within our function :)
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
	let $log := local:log4jDebug( $message )

	return 
	
	    if ( $operation = $CONSTANT:OP-CREATE-MEMBER )
	    then manta-plugin:before-create-member( $request-path-info , $request-data )
	    
        else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
        then manta-plugin:before-update-member( $request-path-info , $request-data )
        
	    else $request-data

};




declare function manta-plugin:before-create-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) 
) as item()*
{
    if ( $request-path-info = "/studies" ) 
    then manta-plugin:before-create-member-study( $request-data )
    else if ( $request-path-info = "/drafts" ) 
    then manta-plugin:before-create-member-drafts( $request-data )
    else $request-data
};




declare function manta-plugin:before-update-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) 
) as item()*
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-study( $request-path-info , $request-data )
    else if ( matches( $request-path-info , "/study-info/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-study-info( $request-path-info , $request-data )
    else if ( matches( $request-path-info , "/drafts/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-drafts( $request-path-info , $request-data )
    else if ( matches( $request-path-info , "/media/submitted/[^/]+/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-submitted-media( $request-path-info , $request-data )
    else if ( matches( $request-path-info , "/media/curated/[^/]+/[^/]+\.atom" ) )
    then manta-plugin:before-update-member-curated-media( $request-path-info , $request-data )
    else $request-data 
};




declare function manta-plugin:before-create-member-study(
    $request-data as element(atom:entry)
) as item()*
{
    let $filtered-request-data := manta-plugin:filter-study-entry( $request-data )
    
    (: create an associated study info entry :)
(:    
    let $study-info-entry := 
        <atom:entry>
            <atom:title type="text">Study Info</atom:title>
        </atom:entry>
        
    let $study-info-entry := atomdb:create-member( "/study-info" , $study-info-entry ) 
    let $study-info-uri := $study-info-entry/atom:link[@rel='edit']/@href
:)    
    (: add link from study to study-info entry :)
(:   
    let $augmented-request-data := 
        <atom:entry>
        {
            $filtered-request-data/attribute::* ,
            $filtered-request-data/child::*
        }
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/studyInfo" href="{$study-info-uri}" type="{$CONSTANT:MEDIA-TYPE-ATOM};type=entry"/>
        </atom:entry>
:)    
    return $filtered-request-data
};



declare function manta-plugin:before-create-member-drafts(
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-draft-entry( $request-data )
    return $request-data
};



declare function manta-plugin:before-update-member-study(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $filtered-request-data := manta-plugin:filter-study-entry( $request-data )
    return $filtered-request-data
};



declare function manta-plugin:before-update-member-study-info(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $stored := atomdb:retrieve-member( $request-path-info )
    let $filtered-request-data := manta-plugin:filter-study-info-entry( $stored , $request-data )
    return $filtered-request-data
};



declare function manta-plugin:before-update-member-drafts(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-draft-entry( $request-data )
    return $request-data
};



declare function manta-plugin:before-update-member-submitted-media(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-media-entry( $request-data )
    return $request-data
};




declare function manta-plugin:before-update-member-curated-media(
    $request-path-info as xs:string ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-media-entry( $request-data )
    return $request-data
};




declare function manta-plugin:filter-study-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $entry , $manta-plugin:reserved )
    
    return $filtered-entry
    
};




declare function manta-plugin:filter-study-info-entry(
    $old as element(atom:entry) ,
    $new as element(atom:entry)
) as element(atom:entry)
{
     
    (: want to carry forward the studyOrigin link stored in the db :)
    let $filtered-entry := atomdb:filter( $old , $new , $manta-plugin:reserved )
    
    return $filtered-entry
    
};




declare function manta-plugin:filter-study-entry(
    $old as element(atom:entry) ,
    $new as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $old , $new , $manta-plugin:reserved )
    
    return $filtered-entry
    
};




declare function manta-plugin:filter-draft-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $entry , $manta-plugin:reserved )
    
    return $filtered-entry
    
};




declare function manta-plugin:filter-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $entry , $manta-plugin:reserved )
    
    return $filtered-entry

};





declare function manta-plugin:after(
	$operation as xs:string ,
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{

	let $message := ( "chassis-manta plugin, after: " , $operation , ", request-path-info: " , $request-path-info ) 
	let $log := local:log4jDebug( $message )

	return
		
		if ( $operation = $CONSTANT:OP-CREATE-MEMBER ) 
		
		then manta-plugin:after-create-member( $request-path-info , $response )
		
		else if ( $operation = $CONSTANT:OP-RETRIEVE-MEMBER )
		
		then manta-plugin:after-retrieve-member( $request-path-info , $response )
		
		else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
		
		then manta-plugin:after-update-member( $request-path-info , $response )
		
		else if ( $operation = $CONSTANT:OP-LIST-COLLECTION )
		
		then manta-plugin:after-list-collection( $request-path-info , $response )
		
		else if ( $operation = $CONSTANT:OP-CREATE-MEDIA )
		
		then manta-plugin:after-create-media( $request-path-info , $response )
		
		else $response

};




(: TODO split media into submitted and curated, and add derivations collection :)


declare function manta-plugin:after-create-member(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{

    if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-create-member-studies( $response )
    
    else if ( $request-path-info = "/drafts" )
    
    then manta-plugin:after-create-member-drafts( $response )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-member-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-create-media(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{

    if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-media-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-retrieve-member(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    
    then manta-plugin:after-retrieve-member-studies( $response )
    
    else if ( matches( $request-path-info , "/drafts/[^/]+\.atom" ) )
    
    then manta-plugin:after-retrieve-member-drafts( $response )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-retrieve-member-submitted-media( $response )
    
    else if ( matches( $request-path-info , "^/media/curated/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-retrieve-member-curated-media( $response )
    
    else if ( matches( $request-path-info , "^/derivations/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-retrieve-member-derivations( $response )
    
    else $response

};



declare function manta-plugin:after-update-member(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
    if ( matches( $request-path-info , "/studies/[^/]+\.atom" ) )
    
    then manta-plugin:after-update-member-studies( $response )
    
    else if ( matches( $request-path-info , "/drafts/[^/]+\.atom" ) )
    
    then manta-plugin:after-update-member-drafts( $response )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+.atom" ) )
    
    then manta-plugin:after-update-member-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-list-collection(
	$request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
    if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-list-collection-studies( $response )
    
    else if ( $request-path-info = "/drafts" )
    
    then manta-plugin:after-list-collection-drafts( $response )
    
    else if ( matches( $request-path-info , "^/media/submitted" ) )
    
    then manta-plugin:after-list-collection-submitted-media( $response )
    
    else if ( matches( $request-path-info , "^/media/curated" ) )
    
    then manta-plugin:after-list-collection-curated-media( $response )
    
    else if ( matches( $request-path-info , "^/derivations" ) )
    
    then manta-plugin:after-list-collection-derivations( $request-path-info , $response )
    
    else if ( matches( $request-path-info , "^/reviews/personal-data/[^/]+" ) )
    
    then manta-plugin:after-list-collection-personal-data-reviews( $request-path-info , $response )

    else $response

};



declare function manta-plugin:after-create-member-studies(
	$response as element(response)
) as element(response)
{

    let $log := local:log4jDebug( "== manta-plugin:after-create-member-studies() ==")
    
    let $entry := $response/body/atom:entry 
    let $id := manta-plugin:get-id( $entry )
    let $path-info := atomdb:edit-path-info($entry)
    let $study-uri := $entry/atom:link[@rel='edit']/@href
    
    (: create study info :)
    
    let $study-info-entry :=
        <atom:entry>
            <atom:title type="text">Study Info for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
        </atom:entry>
        
    (: create the member :)    
    let $member-study-info := atomdb:create-member( "/study-info" , $id , $study-info-entry )  
    
    (: create and store the security descriptor :)
    let $path-info-member-study-info := atomdb:edit-path-info( $member-study-info )
    let $security-descriptor-study-info := security-config:study-info-member-security-descriptor( $study-uri )
    let $descriptor-stored := atomsec:store-descriptor( $path-info-member-study-info , $security-descriptor-study-info )
    
    (: create a collection for submitted media :)    
    
    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $log := local:log4jDebug( $submitted-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Submitted Media for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
            <atombeat:config-link-extensions>
                <atombeat:extension-attribute
                    name="allow"
                    namespace="http://purl.org/atombeat/xmlns">
                    <atombeat:config context="entry-in-feed">
                        <atombeat:param name="match-rels" value="edit-media"/>
                    </atombeat:config>
                    <atombeat:config context="entry">
                        <atombeat:param name="match-rels" value="*"/>
                    </atombeat:config>
                </atombeat:extension-attribute>
            </atombeat:config-link-extensions>
        </atom:feed>
        
    let $submitted-media-collection-db-path := atomdb:create-collection( $submitted-media-collection-path-info , $feed )
    let $submitted-media-collection-descriptor := security-config:submitted-media-collection-security-descriptor( $submitted-media-collection-path-info )
    let $descriptor-stored := atomsec:store-descriptor( $submitted-media-collection-path-info , $submitted-media-collection-descriptor )
    
    (: create a collection for curated media :)    

    let $curated-media-collection-path-info := manta-plugin:curated-media-collection-path-info-for-study-entry( $entry )
    let $log := local:log4jDebug( $curated-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Curated Media for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
            <atombeat:config-link-extensions>
                <atombeat:extension-attribute
                    name="allow"
                    namespace="http://purl.org/atombeat/xmlns">
                    <atombeat:config context="entry-in-feed">
                        <atombeat:param name="match-rels" value="edit-media"/>
                    </atombeat:config>
                </atombeat:extension-attribute>
            </atombeat:config-link-extensions>
        </atom:feed>
        
    let $curated-media-collection-db-path := atomdb:create-collection( $curated-media-collection-path-info , $feed )
    let $curated-media-collection-descriptor := security-config:curated-media-collection-security-descriptor( $curated-media-collection-path-info )
    let $descriptor-stored := atomsec:store-descriptor( $curated-media-collection-path-info , $curated-media-collection-descriptor )
    
    (: create a collection for derivations :)    

    let $derivations-collection-path-info := manta-plugin:derivations-collection-path-info-for-study-entry( $entry )
    let $log := local:log4jDebug( $derivations-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Derivations for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
        </atom:feed>
        
    let $derivations-collection-db-path := atomdb:create-collection( $derivations-collection-path-info , $feed )
    let $derivations-collection-descriptor := security-config:derivations-collection-security-descriptor( $derivations-collection-path-info )
    let $descriptor-stored := atomsec:store-descriptor( $derivations-collection-path-info , $derivations-collection-descriptor )
    
    (: create a collection for personal data reviews :)    

    let $personal-data-reviews-collection-path-info := manta-plugin:personal-data-reviews-collection-path-info-for-study-entry( $entry )
    let $log := local:log4jDebug( $personal-data-reviews-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Personal Data Reviews for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
        </atom:feed>
        
    let $personal-data-reviews-collection-db-path := atomdb:create-collection( $personal-data-reviews-collection-path-info , $feed )
    let $personal-data-reviews-collection-descriptor := security-config:personal-data-reviews-collection-security-descriptor( $personal-data-reviews-collection-path-info )
    let $descriptor-stored := atomsec:store-descriptor( $personal-data-reviews-collection-path-info , $personal-data-reviews-collection-descriptor )
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-create-member-drafts(
	$response as element(response)
) as element(response)
{

    let $log := local:log4jDebug( "== manta-plugin:after-create-member-drafts() ==")
    
    let $entry := $response/body/atom:entry 
    let $id := manta-plugin:get-id( $entry )
    let $user := request:get-attribute( $config:user-name-request-attribute-key )
    
    let $draft-media-collection-path-info := manta-plugin:draft-media-collection-path-info-for-draft-entry( $entry )
    let $log := local:log4jDebug( $draft-media-collection-path-info )
    
    let $feed :=
        <atom:feed>
            <atom:title type="text">Draft Media for Draft {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originDraft" 
                href="{$entry/atom:link[@rel='edit']/@href}" 
                type="application/atom+xml;type=entry"/>
            <atombeat:config-link-extensions>
                <atombeat:extension-attribute
                    name="allow"
                    namespace="http://purl.org/atombeat/xmlns">
                    <atombeat:config context="entry-in-feed">
                        <atombeat:param name="match-rels" value="edit-media"/>
                    </atombeat:config>
                </atombeat:extension-attribute>
            </atombeat:config-link-extensions>
        </atom:feed>
        
    let $draft-media-collection-db-path := atomdb:create-collection( $draft-media-collection-path-info , $feed )
    let $draft-media-collection-descriptor := security-config:draft-media-collection-security-descriptor( $draft-media-collection-path-info , $user )
    let $descriptor-stored := atomsec:store-descriptor( $draft-media-collection-path-info , $draft-media-collection-descriptor )
    
    let $entry := manta-plugin:augment-draft-entry( $entry )
    
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-create-member-submitted-media(
	$response as element(response)
) as element(response)
{

    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-media-entry( $entry )
    
    return manta-plugin:replace-response-body( $response , $entry )
    
};



declare function manta-plugin:after-create-media-submitted-media(
	$response as element(response)
) as element(response)
{

    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-studies(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-study-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-derivations(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-derivation-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-drafts(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-draft-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-submitted-media(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-curated-media(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-update-member-studies(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-study-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-update-member-drafts(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-draft-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-update-member-submitted-media(
	$response as element(response)
) as element(response)
{
    
    let $entry := $response/body/atom:entry 
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-list-collection-studies(
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $feed := 
        <atom:feed>
        {
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            for $entry in $feed/atom:entry
            return manta-plugin:augment-study-entry( $entry )
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-drafts(
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $feed := 
        <atom:feed>
        {
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            for $entry in $feed/atom:entry
            return manta-plugin:augment-draft-entry( $entry )
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-submitted-media(
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            
            for $entry in $feed/atom:entry
            return manta-plugin:augment-media-entry( $entry )
            
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-curated-media(
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            
            for $entry in $feed/atom:entry
            return manta-plugin:augment-media-entry( $entry )
            
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-derivations(
    $request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $param-input := request:get-parameter( "input" , "" )
    let $param-output := request:get-parameter( "output" , "" )
    
    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            
            for $entry in $feed/atom:entry
            return 
                if ( ( 
                        $param-input = "" 
                        or $param-input = $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/derivationInput"]/@href 
                    )
                    and (
                        $param-output = "" 
                        or $param-output = $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/derivationOutput"]/@href 
                    )
                ) 
                then manta-plugin:augment-derivation-entry( $entry )
                else ()
            
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-personal-data-reviews(
    $request-path-info as xs:string ,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 
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
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:submitted-media-collection-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $collection-path-info := concat( "/media/submitted/" , $id )
    return $collection-path-info
};




declare function manta-plugin:draft-media-collection-path-info-for-draft-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $collection-path-info := concat( "/media/draft/" , $id )
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



declare function manta-plugin:study-info-member-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $path-info := concat( "/study-info/" , $id , ".atom" )
    return $path-info
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

    let $entry-path-info := atomdb:edit-path-info( $entry )
    
    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $submitted-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/submittedMedia" 
            href="{concat( $config:content-service-url , $submitted-media-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $curated-media-collection-path-info := manta-plugin:curated-media-collection-path-info-for-study-entry( $entry )
    let $curated-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/curatedMedia" 
            href="{concat( $config:content-service-url , $curated-media-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $derivations-collection-path-info := manta-plugin:derivations-collection-path-info-for-study-entry( $entry )
    let $derivations-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/derivations" 
            href="{concat( $config:content-service-url , $derivations-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $personal-data-reviews-collection-path-info := manta-plugin:personal-data-reviews-collection-path-info-for-study-entry( $entry )
    let $personal-data-reviews-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" 
            href="{concat( $config:content-service-url , $personal-data-reviews-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $study-info-member-path-info := manta-plugin:study-info-member-path-info-for-study-entry( $entry )
    let $study-info-member-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/studyInfo" 
            href="{concat( $config:content-service-url , $study-info-member-path-info )}"
            type="application/atom+xml;type=entry"/>
            
    let $children := (
        <manta:id>{manta-plugin:get-id($entry)}</manta:id> ,
        $submitted-media-collection-link ,
        $curated-media-collection-link ,
        $derivations-collection-link ,
        $personal-data-reviews-collection-link ,
        $study-info-member-link
    )
        
    let $augmented-entry := xutil:append-child( $entry , $children )
            
    return $augmented-entry

};



declare function manta-plugin:augment-derivation-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $entry-path-info := atomdb:edit-path-info( $entry )
    
    let $entry := 
        <atom:entry>
        {
            $entry/attribute::* ,
            for $child in $entry/child::* 
            return 
                if ( 
                    local-name( $child ) = $CONSTANT:ATOM-LINK 
                    and namespace-uri( $child ) = $CONSTANT:ATOM-NSURI 
                    and ( 
                        $child/@rel='http://www.cggh.org/2010/chassis/terms/derivationInput' 
                        or $child/@rel='http://www.cggh.org/2010/chassis/terms/derivationOutput' 
                    )   
                )
                then 
                    <atom:link>
                    {
                        $child/attribute::* ,
                        let $path-info := substring-after( $child/@href , $config:content-service-url )
                        return 
                            if ( atomdb:member-available( $path-info ) )
                            then
                                <ae:inline>
                                {
                                    let $raw-entry := atomdb:retrieve-member( $path-info )
                                    let $augmented-entry := manta-plugin:augment-media-entry( $raw-entry )
                                    return $augmented-entry
                                }
                                </ae:inline>
                            else ()
                    }
                    </atom:link>
                else $child
        }
        </atom:entry>
            
    return $entry

};



declare function manta-plugin:augment-draft-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $draft-media-collection-path-info := manta-plugin:draft-media-collection-path-info-for-draft-entry( $entry )
    let $draft-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/draftMedia" 
            href="{concat( $config:content-service-url , $draft-media-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $entry := 
        <atom:entry>
        {
            $entry/attribute::* ,
            $entry/child::* ,
            $draft-media-collection-link
        }
        </atom:entry>
            
    return $entry

};



declare function manta-plugin:augment-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $id := manta-plugin:get-id($entry)
    
    let $study-id := text:groups( $entry/atom:link[@rel='edit']/@href , "([^/]+)/[^/]+\.atom$" )[2]
    let $origin-study-path-info := concat( "/studies/" , $study-id , ".atom" )
    let $origin-study-uri := concat( $config:content-service-url , $origin-study-path-info )
    
    let $personal-data-reviews-uri := 
        concat( 
            $config:content-service-url , 
            manta-plugin:personal-data-reviews-collection-path-info( $study-id ) ,
            "?reviewSubject=" ,
            $entry/atom:link[@rel='edit']/@href
        )

    let $derivation-uri := concat( $config:content-service-url , "/derivations/" , $study-id , "?output=" , $entry/atom:link[@rel='edit']/@href )
    let $derived-uri := concat( $config:content-service-url , "/derivations/" , $study-id , "?input=" , $entry/atom:link[@rel='edit']/@href )

    let $entry := 
        <atom:entry>
            <manta:id>{$id}</manta:id>
        {
            $entry/attribute::* ,
            $entry/child::* ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="{$origin-study-uri}" type="application/atom+xml;type=entry"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" href="{$personal-data-reviews-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derivation" href="{$derivation-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derived" href="{$derived-uri}" type="application/atom+xml;type=feed"/>
        }
        </atom:entry>
            
    return $entry

};



declare function manta-plugin:replace-response-body( $response as element(response) , $body as item() ) as element(response)
{
    <response>
    {
        $response/status ,
        $response/headers
    }
        <body>{$body}</body>
    </response>
};
