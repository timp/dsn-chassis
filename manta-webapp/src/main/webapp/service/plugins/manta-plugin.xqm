xquery version "1.0";

module namespace manta-plugin = "http://www.cggh.org/2010/chassis/manta/xquery/atombeat-plugin";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
(: see http://tools.ietf.org/html/draft-mehta-atom-inline-01 :)
declare namespace ae = "http://purl.org/atom/ext/" ;
declare namespace manta = "http://www.cggh.org/2010/chassis/manta/xmlns" ;
declare namespace app = "http://www.w3.org/2007/app";

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
import module namespace tombstone-db = "http://purl.org/atombeat/xquery/tombstone-db" at "../lib/tombstone-db.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;

(: These links are added by this plugin, during the after phase, 
   so must be filtered out prior to creation or updating, or multiple 
   copies will result. :)
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
            <link rel="http://www.cggh.org/2010/chassis/terms/groups"/>
            <link rel="http://www.cggh.org/2010/chassis/terms/link"/>
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


declare function manta-plugin:get-request-path-info($request as element(request) ) as xs:string
{
    let $request-path-info := $request/path-info/text()
    let $log := local:log4jDebug($request-path-info)
    return $request-path-info
};

declare function manta-plugin:get-attribute($parameter-name as xs:string, $request as element(request)) as xs:string
{
    let $value := $request/attributes/attribute[name = lower-case($parameter-name)]/value/text()
    return if ( $value castable as xs:string ) then xs:string( $value ) else () 
};

declare function manta-plugin:get-user($request as element(request)) as xs:string
{
    let $value := $request/user/text()
    return if ( $value castable as xs:string ) then xs:string( $value ) else () 
};
declare function manta-plugin:edit-path-info( $entry as element(atom:entry) ) as xs:string?
{
    let $href := $entry/atom:link[@rel='edit']/@href
    return
        if ( exists( $href ) ) then 
            let $uri := $href cast as xs:string
            return
                if ( starts-with( $uri , $config:edit-link-uri-base ) )
                then substring-after( $uri , $config:edit-link-uri-base )
                else ()
        else ()
};
declare function manta-plugin:before(
	$operation as xs:string ,
	$request as element(request),
	$entity as item()*
) as item()*
{
	let $request-data := $entity


	let $message := ( "chassis-manta plugin, before: " , $operation  ) 
	let $log := local:log4jDebug( $message )


	return 
	
	    if ( $operation = $CONSTANT:OP-CREATE-MEMBER )
	    then manta-plugin:before-create-member( $request , $request-data )
	    
        else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
        then manta-plugin:before-update-member( $request , $request-data )
        
	    else $request-data

};




declare function manta-plugin:before-create-member(
	$request as element(request) ,
	$request-data as element(atom:entry) 
) as item()*
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
    
    return if ( $request-path-info = "/studies" ) 
    then manta-plugin:before-create-member-study( $request-data )
    else $request-data
};




declare function manta-plugin:before-update-member(
	$request as element(request) ,
	$request-data as element(atom:entry) 
) as item()*
{
     let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( matches( $request-path-info , "/studies/[^/]+" ) )
    then manta-plugin:before-update-member-study( $request , $request-data )
    else if ( matches( $request-path-info , "/study-info/[^/]+" ) )
    then manta-plugin:before-update-member-study-info( $request , $request-data )
    else if ( matches( $request-path-info , "/media/submitted/[^/]+/[^/]+" ) )
    then manta-plugin:before-update-member-submitted-media( $request , $request-data )
    else if ( matches( $request-path-info , "/media/curated/[^/]+/[^/]+" ) )
    then manta-plugin:before-update-member-curated-media( $request , $request-data )
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

declare function manta-plugin:is-draft($old-entry as element(atom:entry)) as xs:string
{
    if ($old-entry/app:control/draft/text() = 'yes') then
      'yes'
    else 
      'no'
     
};

declare function manta-plugin:before-update-member-study(
    $request as element(request) ,
    $request-data as element(atom:entry)
) as item()*
{
    let $filtered-request-data := manta-plugin:filter-study-entry( $request-data )
    let $member-path-info := manta-plugin:edit-path-info($request-data)
    return if ( atomdb:member-available( $member-path-info ) ) then 
        let $old-entry := atomdb:retrieve-member( $member-path-info )
        let $is-draft := manta-plugin:is-draft( $old-entry )
            (: pass to after phase via request attribute so tombstone can be stored :)
        let $attrs :=  
                <attributes>
                    <attribute>
                        <name>chassis.manta-plugin.is-draft</name>
                        <value>{$is-draft}</value>
                    </attribute>
                </attributes>
        return ($filtered-request-data, $attrs) 
    else
        $filtered-request-data
      
};



declare function manta-plugin:before-update-member-study-info(
    $request as element(request) ,
    $request-data as element(atom:entry)
) as item()*
{
     let $request-path-info := manta-plugin:get-request-path-info($request)
  
    let $stored := atomdb:retrieve-member( $request-path-info )
    let $filtered-request-data := manta-plugin:filter-study-info-entry( $stored , $request-data )
    return $filtered-request-data
};

declare function manta-plugin:before-update-member-submitted-media(
    $request as element(request) ,
    $request-data as element(atom:entry)
) as item()*
{
    let $request-data := manta-plugin:filter-media-entry( $request-data )
    return $request-data
};




declare function manta-plugin:before-update-member-curated-media(
    $request as element(request) ,
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

declare function manta-plugin:filter-media-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $filtered-entry := atomdb:filter( $entry , $manta-plugin:reserved )
    
    return $filtered-entry

};





declare function manta-plugin:after(
	$operation as xs:string ,
    $request as element(request) ,
	$response as element(response)
) as element(response)
{

	let $message := ( "chassis-manta plugin, after: " , $operation ) 
	let $log := local:log4jDebug( $message )

	return
		
		if ( $operation = $CONSTANT:OP-CREATE-MEMBER ) 
		
		then manta-plugin:after-create-member( $request , $response )
		
		else if ( $operation = $CONSTANT:OP-RETRIEVE-MEMBER or $operation = $CONSTANT:OP-ATOM-PROTOCOL-ERROR)
		
		then manta-plugin:after-retrieve-member( $request , $response )
		
		else if ( $operation = $CONSTANT:OP-UPDATE-MEMBER )
		
		then manta-plugin:after-update-member( $request , $response )
		
		else if ( $operation = $CONSTANT:OP-LIST-COLLECTION )
		
		then manta-plugin:after-list-collection( $request , $response )
		
		else if ( $operation = $CONSTANT:OP-CREATE-MEDIA )
		
		then manta-plugin:after-create-media( $request , $response )
		
		else $response

};




(: TODO split media into submitted and curated, and add derivations collection :)


declare function manta-plugin:after-create-member(
	$request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-create-member-studies( $request, $response )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-member-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-create-media(
	$request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( matches( $request-path-info , "^/media/submitted/[^/]+" ) )
    
    then manta-plugin:after-create-media-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-retrieve-member(
	$request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( matches( $request-path-info , "/studies/[^/]+" ) )
    
    then manta-plugin:after-retrieve-member-studies( $response )
    
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+" ) )
    
    then manta-plugin:after-retrieve-member-submitted-media( $response )
    
    else if ( matches( $request-path-info , "^/media/curated/[^/]+/[^/]+" ) )
    
    then manta-plugin:after-retrieve-member-curated-media( $response )
    
    else if ( matches( $request-path-info , "^/derivations/[^/]+/[^/]+" ) )
    
    then manta-plugin:after-retrieve-member-derivations( $response )
    
    else $response

};



declare function manta-plugin:after-update-member(
	$request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( matches( $request-path-info , "/studies/[^/]+" ) )
    
    then manta-plugin:after-update-member-studies( $request, $response )
   
    else if ( matches( $request-path-info , "^/media/submitted/[^/]+/[^/]+" ) )
    
    then manta-plugin:after-update-member-submitted-media( $response )
    
    else $response

};



declare function manta-plugin:after-list-collection(
	$request as element(request) ,
	$response as element(response)
) as element(response)
{
    let $request-path-info := manta-plugin:get-request-path-info($request)
  
    return if ( $request-path-info = "/studies" )
    
    then manta-plugin:after-list-collection-studies( $response )
  
    else if ( matches( $request-path-info , "^/media/submitted" ) )
    
    then manta-plugin:after-list-collection-submitted-media( $response )
    
    else if ( matches( $request-path-info , "^/media/curated" ) )
    
    then manta-plugin:after-list-collection-curated-media( $response )
    
    else if ( matches( $request-path-info , "^/derivations" ) )
    
    then manta-plugin:after-list-collection-derivations( $request , $response )
    
    else if ( matches( $request-path-info , "^/reviews/personal-data/[^/]+" ) )
    
    then manta-plugin:after-list-collection-personal-data-reviews( $request , $response )
    
    else if ( matches( $request-path-info , "^/link" ) )
    
    then manta-plugin:after-list-collection-link( $request , $response )

    else $response

};



declare function manta-plugin:after-create-member-studies(
    $request as element(request) ,
	$response as element(response)
) as element(response)
{

    let $log := local:log4jDebug( "== manta-plugin:after-create-member-studies() ==")
    
    let $entry := $response/body/atom:entry 
    let $id := manta-plugin:get-id( $entry )
    let $path-info := manta-plugin:edit-path-info($entry)
    let $study-uri := $entry/atom:link[@rel='edit']/@href
    let $user-name := manta-plugin:get-user( $request )
    
 (: create group :)
    let $study-group-entry :=
        <atom:entry>
            <atom:title type="text">Group</atom:title>
            <atom:content>
                <atombeat:groups xmlns:atombeat="http://purl.org/atombeat/xmlns">
                    <atombeat:group id="GROUP_ADMINISTRATORS">
                        <atombeat:member>{$user-name}</atombeat:member>
                    </atombeat:group>
                </atombeat:groups>
            </atom:content>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
        </atom:entry>
        
        
    (: create the group member :)
    let $member-groups := atomdb:create-member( "/groups" , $id , $study-group-entry, $user-name )
    (: create and store the security descriptor :)
    let $path-info-member-group := manta-plugin:edit-path-info( $member-groups )
    
    let $group-uri := concat($config:edit-link-uri-base,$path-info-member-group)
    (: Now that the group has been created update the study security descriptor :)
       (: create and store the security descriptor :)    
    
    let $security-descriptor-groups := security-config:group-security-descriptor( $group-uri )
    let $descriptor-stored := atomsec:store-descriptor( $path-info-member-group , $security-descriptor-groups )
    

    
    let $security-descriptor-study := security-config:study-member-security-descriptor( $group-uri )
    let $study-security-descriptor-path := $path-info
    let $descriptor-stored := atomsec:store-descriptor( $study-security-descriptor-path , $security-descriptor-study )

    (: create study info :)
    
    let $study-info-entry :=
        <atom:entry>
            <atom:title type="text">Study Info for Study {$id}</atom:title>
            <atom:link 
                rel="http://www.cggh.org/2010/chassis/terms/originStudy" 
                href="{$study-uri}" 
                type="application/atom+xml;type=entry"/>
        </atom:entry>
        
    (: create the study-info member :)
    let $member-study-info := atomdb:create-member( "/study-info" , $id , $study-info-entry, $user-name )  
    
    (: create and store the security descriptor :)
    let $path-info-member-study-info := manta-plugin:edit-path-info( $member-study-info )
    let $security-descriptor-study-info := security-config:study-info-member-security-descriptor( $group-uri )
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
                    <atombeat:config context="feed">
                        <atombeat:param name="match-rels" value="http://purl.org/atombeat/rel/security-descriptor"/>
                    </atombeat:config>
                </atombeat:extension-attribute>
            </atombeat:config-link-extensions>
            <atombeat:config-tombstones>
                <atombeat:config>
                    <atombeat:param name="ghost-atom-elements" value="title author summary category published updated"/>
                </atombeat:config>
            </atombeat:config-tombstones>
        </atom:feed>
    let $user-name := manta-plugin:get-user( $request )    
    let $submitted-media-collection-db-path := atomdb:create-collection( $submitted-media-collection-path-info , $feed, $user-name )
    let $submitted-media-collection-descriptor := security-config:submitted-media-collection-security-descriptor( $group-uri )
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
                    <atombeat:config context="feed">
                        <atombeat:param name="match-rels" value="http://purl.org/atombeat/rel/security-descriptor"/>
                    </atombeat:config>
                </atombeat:extension-attribute>
            </atombeat:config-link-extensions>
        </atom:feed>
        
    let $curated-media-collection-db-path := atomdb:create-collection( $curated-media-collection-path-info , $feed, $user-name )
    let $curated-media-collection-descriptor := security-config:curated-media-collection-security-descriptor( $group-uri )
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
        
    let $derivations-collection-db-path := atomdb:create-collection( $derivations-collection-path-info , $feed, $user-name )
    let $derivations-collection-descriptor := security-config:derivations-collection-security-descriptor( $group-uri )
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
        
    let $personal-data-reviews-collection-db-path := atomdb:create-collection( $personal-data-reviews-collection-path-info , $feed, $user-name )
    let $personal-data-reviews-collection-descriptor := security-config:personal-data-reviews-collection-security-descriptor( $group-uri )
    let $descriptor-stored := atomsec:store-descriptor( $personal-data-reviews-collection-path-info , $personal-data-reviews-collection-descriptor )
    
    let $entry := manta-plugin:augment-study-entry( $entry )
    
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
    
    let $entry := manta-plugin:get-entry($response/body) 
    let $entry := manta-plugin:augment-study-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-derivations(
	$response as element(response)
) as element(response)
{
    
    let $entry := manta-plugin:get-entry($response/body) 
    let $entry := manta-plugin:augment-derivation-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};

declare function manta-plugin:after-retrieve-member-submitted-media(
	$response as element(response)
) as element(response)
{
    let $entry := manta-plugin:get-entry($response/body)
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};




declare function manta-plugin:after-retrieve-member-curated-media(
	$response as element(response)
) as element(response)
{
    
    let $entry := manta-plugin:get-entry($response/body) 
    let $entry := manta-plugin:augment-media-entry( $entry )
    return manta-plugin:replace-response-body( $response , $entry )
    
};

declare function manta-plugin:enable-tombstones($feed as element(atom:feed)) as element(atom:feed) {
        <atom:feed atombeat:enable-tombstones="true"> 
        {        
            $feed/attribute::* ,
            $feed/child::*
        }
        </atom:feed>

};

declare function manta-plugin:submitted-media-enable-tombstones(
        $request as element(request) ,
        $entry as element(atom:entry)) as element(atom:feed) {
   let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $log := local:log4jDebug( $submitted-media-collection-path-info )
    
    let $old-feed := atomdb:retrieve-feed-without-entries($submitted-media-collection-path-info)
    let $feed := manta-plugin:enable-tombstones($old-feed)
    let $user-name := manta-plugin:get-user( $request )    
    let $submitted-media-collection-db-path := atomdb:update-collection( $submitted-media-collection-path-info , $feed)
    return $feed
};


declare function manta-plugin:after-update-member-studies(
    $request as element(request),
	$response as element(response)
) as element(response)
{   
    let $entry := $response/body/atom:entry
    let $was-draft := $request/attributes/attribute[name='chassis.manta-plugin.is-draft']/value/text()
    let $is-draft := manta-plugin:is-draft($entry)
    let $x := if ($was-draft = 'yes' and $is-draft = 'no') then
        let $path-info := manta-plugin:edit-path-info($entry)
        let $old-security-descriptor := atomsec:retrieve-descriptor($path-info)
        let $new-descriptor := security-config:remove-permission($old-security-descriptor,'ALLOW','DELETE_MEMBER','GROUP_ADMINISTRATORS')
        let $study-security-descriptor-path := $path-info
        let $descriptor-stored := atomsec:store-descriptor( $study-security-descriptor-path , $new-descriptor )
        
        let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
        let $old-sm-security-descriptor := atomsec:retrieve-descriptor($submitted-media-collection-path-info)
        let $sm-sd-1 := security-config:remove-permission($old-sm-security-descriptor,'ALLOW','DELETE_MEDIA','GROUP_ADMINISTRATORS')
        let $submitted-media-collection-descriptor := security-config:remove-permission($sm-sd-1,'ALLOW','DELETE_MEMBER','GROUP_ADMINISTRATORS')
        let $descriptor-stored := atomsec:store-descriptor( $submitted-media-collection-path-info , $submitted-media-collection-descriptor )
        
        let $nf := manta-plugin:submitted-media-enable-tombstones($request, $entry)
        return $new-descriptor
        else ()
        
    
    let $entry := manta-plugin:augment-study-entry( $entry )
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

declare function manta-plugin:after-list-collection-submitted-media(
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 
    
    let $feed := 
        <atom:feed>
        {        
            $feed/attribute::* ,
            $feed/child::*[ not( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ],
            
            for $entry in $feed/child::*[. instance of element(atom:entry) or . instance of element(at:deleted-entry) ]
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
            $feed/child::*[ not( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ],
            
            for $entry in $feed/child::*[. instance of element(atom:entry) or . instance of element(at:deleted-entry) ]
            return manta-plugin:augment-media-entry( $entry )
            
        }
        </atom:feed>
    
    return manta-plugin:replace-response-body( $response , $feed )
    
};




declare function manta-plugin:after-list-collection-derivations(
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $param-input := xutil:get-parameter( "input" , $request)
    let $param-output := xutil:get-parameter( "output" , $request )

    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ],
            
            for $entry in $feed/child::*[ ( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ]
            return 
                if ( ( 
                        empty($param-input) 
                        or $param-input = $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/derivationInput"]/@href 
                    )
                    and (
                        empty($param-output) 
                        or $param-output = $entry/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/derivationOutput"]/@href 
                    )
                ) 
                then manta-plugin:augment-derivation-entry( $entry )
                else ()
            
        }
        </atom:feed>

    return manta-plugin:replace-response-body( $response , $feed )
    
};

declare function manta-plugin:after-list-collection-link(
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 

    let $param-study := xutil:get-parameter( "study" , $request)

    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ],
            
            for $entry in $feed/child::*[ ( . instance of element(atom:entry) or . instance of element(at:deleted-entry) ) ]
            return 
                if ( empty($param-study) 
                        or $param-study = $entry/atom:content/atom:link[@rel="http://www.cggh.org/2010/chassis/terms/linkMember"]/@href 
                    )
                then ( $entry )
                else ()
            
        }
        </atom:feed>

    return manta-plugin:replace-response-body( $response , $feed )
    
};



declare function manta-plugin:after-list-collection-personal-data-reviews(
    $request as element(request) ,
	$response as element(response)
) as element(response)
{
    
    let $feed := $response/body/atom:feed 
    let $param-review-subject := xutil:get-parameter( "reviewSubject" , $request )
    
    let $feed := 
        <atom:feed>
        {
        
            $feed/attribute::* ,
            $feed/child::*[ not( local-name(.) = $CONSTANT:ATOM-ENTRY and namespace-uri(.) = $CONSTANT:ATOM-NSURI ) ] ,
            
            for $entry in $feed/atom:entry
            return 
                if ( 
                    empty($param-review-subject) 
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
    let $path-info := concat( "/study-info/" , $id )
    return $path-info
};


declare function manta-plugin:groups-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{
    let $id := manta-plugin:get-id( $entry )
    let $path-info := concat( "/groups/" , $id )
    return $path-info
};


declare function manta-plugin:link-path-info-for-study-entry(
    $entry as element(atom:entry)
) as xs:string
{   
    let $path-info := concat( "/link?study=" , $entry//atom:link[@rel = 'self']/@href )
    return $path-info
};



declare function manta-plugin:get-id(
    $entry as element(atom:entry)
) as xs:string
{
    replace( $entry/atom:id/text() , '^.*/([^/^.]+)$', '$1' )
};




declare function manta-plugin:get-deleted-entry-id(
    $entry as element(at:deleted-entry)
) as xs:string
{
    replace( $entry/@ref , '^.*/([^/^.]+)$', '$1' )
};





declare function manta-plugin:augment-study-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $entry-path-info := manta-plugin:edit-path-info( $entry )
    
    let $submitted-media-collection-path-info := manta-plugin:submitted-media-collection-path-info-for-study-entry( $entry )
    let $submitted-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/submittedMedia" 
            href="{concat( $config:self-link-uri-base , $submitted-media-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $curated-media-collection-path-info := manta-plugin:curated-media-collection-path-info-for-study-entry( $entry )
    let $curated-media-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/curatedMedia" 
            href="{concat( $config:self-link-uri-base , $curated-media-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $derivations-collection-path-info := manta-plugin:derivations-collection-path-info-for-study-entry( $entry )
    let $derivations-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/derivations" 
            href="{concat( $config:self-link-uri-base , $derivations-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $personal-data-reviews-collection-path-info := manta-plugin:personal-data-reviews-collection-path-info-for-study-entry( $entry )
    let $personal-data-reviews-collection-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" 
            href="{concat( $config:self-link-uri-base , $personal-data-reviews-collection-path-info )}"
            type="application/atom+xml;type=feed"/>
        
    let $study-info-member-path-info := manta-plugin:study-info-member-path-info-for-study-entry( $entry )
    let $study-info-member-link := 
        <atom:link 
            rel="http://www.cggh.org/2010/chassis/terms/studyInfo" 
            href="{concat( $config:self-link-uri-base , $study-info-member-path-info )}"
            type="application/atom+xml;type=entry"/>

    let $groups-path-info := manta-plugin:groups-path-info-for-study-entry( $entry )
    let $groups-link := 
        <atom:link
            rel="http://www.cggh.org/2010/chassis/terms/groups"
            href="{concat( $config:self-link-uri-base , $groups-path-info )}"
            type="application/atom+xml;type=entry"/>
    
    let $link-path-info := manta-plugin:link-path-info-for-study-entry( $entry )
    let $link-link := 
        <atom:link
            rel="http://www.cggh.org/2010/chassis/terms/link"
            href="{concat( $config:self-link-uri-base , $link-path-info )}"
            type="application/atom+xml;type=entry"/>
            
    let $children := (
        <manta:id>{manta-plugin:get-id($entry)}</manta:id> ,
        $submitted-media-collection-link ,
        $curated-media-collection-link ,
        $derivations-collection-link ,
        $personal-data-reviews-collection-link ,
        $study-info-member-link ,
        $groups-link,
        $link-link
    )
        
    let $augmented-entry := xutil:append-child( $entry , $children )
            
    return $augmented-entry

};



declare function manta-plugin:augment-derivation-entry(
    $entry as element()
) as element()
{
    let $ret := $entry
    let $ret := if ($entry instance of element(atom:entry)) then
        let $new-entry := manta-plugin:augment-derivation-atom-entry($entry)
        return $new-entry
     else if ($entry instance of element(at:deleted-entry)) then
        let $new-entry := manta-plugin:augment-derivation-tombstone($entry)
        return $new-entry
     else 
         let $new-entry := $entry
         return $new-entry
    return $ret
};

declare function manta-plugin:expand-atom-link($path-info as xs:string) as element(ae:inline)
{
if ( atomdb:member-available( $path-info ) )
                            then
                                <ae:inline>
                                {
                                    let $raw-entry := atomdb:retrieve-member( $path-info )
                                    
                                    let $augmented-entry := manta-plugin:augment-media-entry( $raw-entry )
                                    return $augmented-entry
                                }
                                </ae:inline>
                            else if ( tombstone-db:tombstone-available( $path-info ) )
                            then
                                <ae:inline>
                                {
                                    let $deleted-entry := tombstone-db:retrieve-tombstone( $path-info )
                                    let $augmented-entry := manta-plugin:augment-media-entry( $deleted-entry )
                                    return $augmented-entry
                                }
                                </ae:inline>
                            else ()
};

declare function manta-plugin:augment-derivation-atom-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{

    let $entry-path-info := manta-plugin:edit-path-info( $entry )
    
    let $entry := 
        <atom:entry>
        {
            $entry/attribute::* ,
            for $child in $entry/child::* 
            return 
                if ( $child instance of element(atom:link)
                    and ( 
                        $child/@rel='http://www.cggh.org/2010/chassis/terms/derivationInput' 
                        or $child/@rel='http://www.cggh.org/2010/chassis/terms/derivationOutput' 
                    )   
                )
                then 
                    <atom:link>
                    {
                        $child/attribute::* ,
                        let $path-info := substring-after( $child/@href , $config:self-link-uri-base )
                        return 
                            manta-plugin:expand-atom-link($path-info)
                            
                    }
                    </atom:link>
                else $child
        }
        </atom:entry>
            
    return $entry

};

declare function manta-plugin:augment-derivation-tombstone(
    $entry as element()
) as element()
{

    let $entry-path-info := manta-plugin:edit-path-info( $entry )
    
    let $entry := 
        <at:deleted-entry>
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
                        let $path-info := substring-after( $child/@href , $config:self-link-uri-base )
                        return 
                            manta-plugin:expand-atom-link($path-info)
                    }
                    </atom:link>
                else $child
        }
        </at:deleted-entry>
            
    return $entry

};

declare function manta-plugin:augment-media-entry(
    $entry as element()
) as element()
{
    let $entry := if ($entry instance of element(atom:entry)) then
        let $ret := manta-plugin:augment-media-atom-entry($entry)
        return $ret
      else 
          let $ret := manta-plugin:augment-media-entry-tombstone($entry)
          return $ret
    return $entry
};

declare function manta-plugin:augment-media-atom-entry(
    $entry as element(atom:entry)
) as element(atom:entry)
{
    let $id := manta-plugin:get-id($entry)
    
    let $study-id := text:groups( $entry/atom:link[@rel='edit']/@href , "([^/]+)/[^/]+$" )[2]
    let $origin-study-path-info := concat( "/studies/" , $study-id )
    let $origin-study-uri := concat( $config:self-link-uri-base , $origin-study-path-info )
    
    let $personal-data-reviews-uri := 
        concat( 
            $config:self-link-uri-base , 
            manta-plugin:personal-data-reviews-collection-path-info( $study-id ) ,
            "?reviewSubject=" ,
            $entry/atom:link[@rel='edit']/@href
        )
    let $derivation-uri := concat( $config:self-link-uri-base , "/derivations/" , $study-id , "?output=" , $entry/atom:link[@rel='edit']/@href )
    let $derived-uri := concat( $config:self-link-uri-base , "/derivations/" , $study-id , "?input=" , $entry/atom:link[@rel='edit']/@href )

    let $groups-path-info := concat( $config:self-link-uri-base , "/groups/" , $study-id) 
    let $groups-link := 
        <atom:link
            rel="http://www.cggh.org/2010/chassis/terms/groups"
            href="{$groups-path-info}"
            type="application/atom+xml;type=entry"/>
            
    let $entry := 
        <atom:entry>
        {
            $entry/attribute::* , (: attributes must come first! :)
            <manta:id>{$id}</manta:id> ,
            $entry/child::* ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="{$origin-study-uri}" type="application/atom+xml;type=entry" manta:idref="{$study-id}"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" href="{$personal-data-reviews-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derivation" href="{$derivation-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derived" href="{$derived-uri}" type="application/atom+xml;type=feed"/>,
            $groups-link
        }
        </atom:entry>

    return $entry

};

declare function manta-plugin:augment-media-entry-tombstone(
    $entry as element(at:deleted-entry)
) as element(at:deleted-entry)
{ 
    let $ref := $entry/@ref
    let $id := manta-plugin:get-deleted-entry-id($entry)
    let $log := local:log4jDebug(concat('augment-media-entry-tombstone:',$ref))
    let $log := local:log4jDebug($entry)
    let $study-id := text:groups( $ref , "([^/]+)/[^/]+$" )[2]
    let $log := local:log4jDebug(concat('augment-media-entry-tombstone:',$study-id))
    let $origin-study-path-info := concat( "/studies/" , $study-id )
    let $origin-study-uri := concat( $config:self-link-uri-base , $origin-study-path-info )
    
    let $personal-data-reviews-uri := 
        concat( 
            $config:self-link-uri-base , 
            manta-plugin:personal-data-reviews-collection-path-info( $study-id ) ,
            "?reviewSubject=" ,
            $ref
        )

    let $derivation-uri := concat( $config:self-link-uri-base , "/derivations/" , $study-id , "?output=" , $ref )
    let $derived-uri := concat( $config:self-link-uri-base , "/derivations/" , $study-id , "?input=" , $ref )

    let $entry := 
        <at:deleted-entry>
        {
            $entry/attribute::* , (: attributes must come first! :)
            <manta:id>{$id}</manta:id> ,
            $entry/child::* ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/originStudy" href="{$origin-study-uri}" type="application/atom+xml;type=entry" manta:idref="{$study-id}"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/personalDataReviews" href="{$personal-data-reviews-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derivation" href="{$derivation-uri}" type="application/atom+xml;type=feed"/> ,
            <atom:link rel="http://www.cggh.org/2010/chassis/terms/derived" href="{$derived-uri}" type="application/atom+xml;type=feed"/>
        }
        </at:deleted-entry>
    return $entry

};

(: Return either an entry or a tombstone :)
declare function manta-plugin:get-entry($unknown as item()) as element() {
     let $ret := if ($unknown/child::* instance of element(atom:entry)) then
        let $new-entry := $unknown/atom:entry
        return $new-entry
     else if ($unknown/child::* instance of element(at:deleted-entry)) then
        let $new-entry := $unknown/at:deleted-entry
        return $new-entry
     else 
         let $new-entry := $unknown
         return $new-entry
(:   let $msg := local:log4jDebug("get-entry")
     let $msg := local:log4jDebug($ret)
   :)
   return $ret
};

declare function manta-plugin:after-error(
    $operation as xs:string ,
    $request as element(request) ,
    $response as element(response)
) as element(response)
{
	let $log := local:log4jDebug( "manta-plugin after error" )
	let $log1 := local:log4jDebug($request)
(: This plugin is agnostic as to whether the entry is atom:entry or at:deleted-entry :)
    let $ret := if ($response/body/child::* instance of element(at:deleted-entry)) then
            manta-plugin:after($operation,$request,$response)
        else
            $response
    return $ret
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
