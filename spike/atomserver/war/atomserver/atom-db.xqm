xquery version "1.0";

module namespace adb = "http://www.cggh.org/2010/xquery/atom-db";

declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace ar = "http://purl.org/atompub/revision/1.0" ;

import module namespace text = "http://exist-db.org/xquery/text" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace util = "http://exist-db.org/xquery/util" ;

import module namespace utilx = "http://www.cggh.org/2010/xquery/util" at "util.xqm" ;
import module namespace af = "http://www.cggh.org/2010/xquery/atom-format" at "atom-format.xqm" ;
import module namespace config = "http://www.cggh.org/2010/xquery/atom-config" at "atom-config.xqm" ;



declare function adb:collection-available(
	$request-path-info as xs:string
) as xs:boolean
{

	(:
	 : We need to know two things to determine whether an atom collection is
	 : available (i.e., exists) for the given request path info. First, does
	 : a database collection exist at the corresponding path? Second, does an
	 : atom feed document exist within that collection?
	 :)
	 
	(:
	 : Map the request path info, e.g., "/foo", to a database collection path,
	 : e.g., "/db/foo".
	 :)
	 
	let $db-collection-path := adb:request-path-info-to-db-path( $request-path-info )

	(:
	 : Obtain the database path for the atom feed document in the given database
	 : collection. Currently, this appends ".feed" to the database collection
	 : path.
	 :)
	 
	let $feed-doc-db-path := adb:feed-doc-db-path( $db-collection-path )
	
	let $available := 
		( xmldb:collection-available( $db-collection-path ) and exists( doc( $feed-doc-db-path ) ) )
	
	return $available
	
};




declare function adb:member-available(
	$request-path-info as xs:string
) as xs:boolean
{

	(:
	 : To determine whether an atom collection member is available (i.e., exists) 
	 : for the given request path info, we map the request path info to a resource
	 : path and check the document exists.
	 :)
	 
	(:
	 : Map the request path info, e.g., "/foo/bar", to a database resource path,
	 : e.g., "/db/foo/bar".
	 :)
	 
	let $member-db-path := adb:request-path-info-to-db-path( $request-path-info )
		
	return ( not( util:binary-doc-available( $member-db-path ) ) and exists( doc( $member-db-path ) ) )
	
};




declare function adb:media-resource-available(
	$request-path-info as xs:string
) as xs:boolean
{

	(:
	 : To determine whether a media resource is available (i.e., exists) 
	 : for the given request path info, we map the request path info to a resource
	 : path and check the document exists.
	 :)
	 
	(:
	 : Map the request path info, e.g., "/foo/bar", to a database resource path,
	 : e.g., "/db/foo/bar".
	 :)
	 
	let $member-db-path := adb:request-path-info-to-db-path( $request-path-info )
		
	return util:binary-doc-available( $member-db-path ) 
	
};




declare function adb:request-path-info-to-db-path( 
	$request-path-info as xs:string
) as xs:string
{
	concat( $config:base-collection-path , $request-path-info )
};




declare function adb:feed-doc-db-path(
	$db-collection-path as xs:string
) as xs:string
{

	if ( ends-with( $db-collection-path , "/" ) )
	then concat( $db-collection-path , $config:feed-doc-name )
	else concat( $db-collection-path , "/", $config:feed-doc-name )
	
};




declare function adb:create-collection(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed) ,
	$enable-versioning as xs:boolean
) as xs:string
{

	if ( adb:collection-available( $request-path-info ) )
	
	then ()
	
	else
		
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )
	
		let $collection-db-path := utilx:get-or-create-collection( $collection-db-path )
		
		let $collection-config :=
		
			<collection xmlns="http://exist-db.org/collection-config/1.0">
			    <triggers>
				{

					(:
					 : EXPERIMENTAL: enable versioning support for the new collection.
					 : TODO make this configurable.
					 :)
					 
					if ( $enable-versioning ) then 
			        <trigger event="store,remove,update" class="org.exist.versioning.VersioningTrigger">
			            <parameter name="overwrite" value="yes"/>
			        </trigger>
			        else ()

				}
			    </triggers>
			</collection>
			
		let $config-collection-path := concat( "/db/system/config" , $collection-db-path )
		let $log := util:log( "debug" , concat( "$config-collection-path: " , $config-collection-path ) )
		
		let $config-collection-path := utilx:get-or-create-collection( $config-collection-path )
		let $log := util:log( "debug" , concat( "$config-collection-path: " , $config-collection-path ) )
		
		let $config-resource-path := xmldb:store( $config-collection-path , "collection.xconf" , $collection-config )
		let $log := util:log( "debug" , concat( "$config-resource-path: " , $config-resource-path ) )

		(:
		 : Obtain the database path for the atom feed document in the given database
		 : collection. Currently, this appends ".feed" to the database collection
		 : path.
		 :)
		 
		let $feed-doc-db-path := adb:feed-doc-db-path( $collection-db-path )

		let $feed := adb:create-feed( $request-path-info , $request-data )
		
		return xmldb:store( $collection-db-path , $config:feed-doc-name , $feed )
			
};




declare function adb:update-collection(
	$request-path-info as xs:string ,
	$request-data as element(atom:feed)
) as xs:string
{

	if ( not( adb:collection-available( $request-path-info ) ) )
	
	then ()
	
	else
		
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )

		let $collection-db-path := utilx:get-or-create-collection( $collection-db-path )

		(:
		 : Obtain the database path for the atom feed document in the given database
		 : collection. Currently, this appends ".feed" to the database collection
		 : path.
		 :)
		 
		let $feed-doc-db-path := adb:feed-doc-db-path( $collection-db-path )

		let $feed := adb:update-feed( doc( $feed-doc-db-path )/atom:feed , $request-data )
		
		return xmldb:store( $collection-db-path , $config:feed-doc-name , $feed )
			
};




declare function adb:create-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) ,
	$comment as xs:string?
) as xs:string
{

	if ( not( adb:collection-available( $request-path-info ) ) )
	
	then ()
	
	else
		
	    let $uuid := util:uuid()
	    
	    let $entry := adb:create-entry( $request-path-info, $request-data , $uuid , $comment )
	    
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )

	    let $entry-doc-db-path := xmldb:store( $collection-db-path , concat( $uuid , ".atom" ) , $entry )    
	    
	    return $entry-doc-db-path
		
};




declare function adb:update-member(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry) ,
	$comment as xs:string?
) as xs:string
{

	if ( not( adb:member-available( $request-path-info ) ) )
	
	then ()
	
	else
		
		(:
		 : Map the request path info, e.g., "/foo/bar", to a database path,
		 : e.g., "/db/foo/bar".
		 :)
		 
		let $member-db-path := adb:request-path-info-to-db-path( $request-path-info )
	
		let $entry := adb:update-entry( doc( $member-db-path )/atom:entry , $request-data , $comment )

		let $groups := text:groups( $request-path-info , "^(.*)/([^/]+)$" )
		
		let $collection-db-path := adb:request-path-info-to-db-path( $groups[2] )
		
		let $entry-uuid := $groups[3]

		return xmldb:store( $collection-db-path , $entry-uuid , $entry )

};




declare function adb:mutable-feed-children(
    $request-data as element(atom:feed)
) as element()*
{
    for $child in $request-data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $af:nsuri and $local-name = $af:id ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:updated ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "self" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:entry )
    return $child
};




declare function adb:mutable-entry-children(
    $request-data as element(atom:entry)
) as element()*
{
    for $child in $request-data/*
    let $namespace-uri := namespace-uri($child)
    let $local-name := local-name($child)
    where
        not( $namespace-uri = $af:nsuri and $local-name = $af:id ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:updated ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:published ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "self" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "edit-media" ) and
        not( $namespace-uri = $af:nsuri and $local-name = $af:link and $child/@rel = "history" ) and 
        not( $namespace-uri = "http://purl.org/atompub/revision/1.0" and $local-name = "comment" )
    return $child
};



declare function adb:create-feed( 
    $request-path-info as xs:string ,
    $request-data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $id := concat( $config:service-url , $request-path-info )
    let $updated := current-dateTime()
    let $self-uri := $id
    let $edit-uri := $id
    
    return
    
        <atom:feed>
            <atom:id>{$id}</atom:id>
            <atom:updated>{$updated}</atom:updated>
            <atom:link rel="self" type="application/atom+xml" href="{$self-uri}"/>
            <atom:link rel="edit" type="application/atom+xml" href="{$edit-uri}"/>
            {
                adb:mutable-feed-children($request-data)
            }
        </atom:feed>  

};




declare function adb:update-feed( 
    $feed as element(atom:feed) ,
    $request-data as element(atom:feed)
) as element(atom:feed) 
{

    (: TODO validate input data :)
    
    let $updated := current-dateTime()
    let $log := util:log( "debug" , concat( "$updated: " , $updated ) )

    return
    
        <atom:feed>
            {
                $feed/atom:id
            }
            <atom:updated>{$updated}</atom:updated>
            {
                $feed/atom:link[@rel="self"] ,
                $feed/atom:link[@rel="edit"] ,
                adb:mutable-feed-children($request-data)
            }
        </atom:feed>  
};




declare function adb:create-entry(
	$request-path-info as xs:string ,
    $request-data as element(atom:entry) ,
    $uuid as xs:string ,
    $comment as xs:string?
) as element(atom:entry)
{

	(:
	 : TODO hide history link for entries in collections where history is not
	 : enabled.
	 :)
	 
    let $id := concat( $config:service-url , $request-path-info , "/" , $uuid , ".atom" )
    let $published := current-dateTime()
    let $updated := $published
    let $self-uri := $id
    let $edit-uri := $id
    
    let $history-uri := concat( $config:history-service-url , $request-path-info , "/" , $uuid , ".atom" )
    
    let $comment := if ( empty( $comment ) or $comment = "" ) then "initial revision" else $comment
    
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    
    let $revision-comment := 
        <ar:comment>
            <atom:author>
                {
                    if ( $config:user-name-is-email ) then
                    <atom:email>{$user-name}</atom:email>
                    else
                    <atom:name>{$user-name}</atom:name>                    
                }
                <atom:updated>{$published}</atom:updated>
                <atom:summary>{$comment}</atom:summary>
            </atom:author>
        </ar:comment>
        
    return
	
        <atom:entry>
            <atom:id>{$id}</atom:id>
            <atom:published>{$published}</atom:published>
            <atom:updated>{$updated}</atom:updated>
            <atom:link rel="self" type="application/atom+xml" href="{$self-uri}"/>
            <atom:link rel="edit" type="application/atom+xml" href="{$edit-uri}"/>
            <atom:link rel="history" type="application/atom+xml" href="{$history-uri}"/>
            {
                $revision-comment ,
                adb:mutable-entry-children($request-data)
            }
        </atom:entry>  
     
};




declare function adb:create-media-link-entry(
	$request-path-info as xs:string ,
    $uuid as xs:string ,
    $media-type as xs:string ,
    $media-link-title as xs:string? ,
    $media-link-summary as xs:string? ,
    $comment as xs:string?
) as element(atom:entry)
{

	(:
	 : TODO hide history link for entries in collections where history is not
	 : enabled.
	 :)
	 
    let $id := concat( $config:service-url , $request-path-info , "/" , $uuid , ".atom" )
    let $log := util:log( "debug", $id )
    
    let $published := current-dateTime()
    let $updated := $published
    let $self-uri := $id
    let $edit-uri := $id
    let $media-uri := concat( $config:service-url , $request-path-info , "/" , $uuid , ".media" )
    
    let $history-uri := concat( $config:history-service-url , $request-path-info , "/" , $uuid , ".atom" )

    let $title :=
    	if ( $media-link-title ) then $media-link-title
    	else concat( "download-" , $uuid , ".media" )
    	
    let $summary :=
    	if ( $media-link-summary ) then $media-link-summary
    	else concat( "media resource (" , $media-type , ")" )
    	
        let $comment := if ( empty( $comment ) or $comment = "" ) then "initial revision" else $comment
    
    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    
    let $revision-comment := 
        <ar:comment>
            <atom:author>
                {
                    if ( $config:user-name-is-email ) then
                    <atom:email>{$user-name}</atom:email>
                    else
                    <atom:name>{$user-name}</atom:name>                    
                }
                <atom:updated>{$published}</atom:updated>
                <atom:summary>{$comment}</atom:summary>
            </atom:author>
        </ar:comment>
    
	return
	
        <atom:entry>
            <atom:id>{$id}</atom:id>
            <atom:published>{$published}</atom:published>
            <atom:updated>{$updated}</atom:updated>
            <atom:link rel="self" type="application/atom+xml" href="{$self-uri}"/>
            <atom:link rel="edit" type="application/atom+xml" href="{$edit-uri}"/>
            <atom:link rel="edit-media" type="{$media-type}" href="{$media-uri}"/>
            <atom:link rel="history" type="application/atom+xml" href="{$history-uri}"/>
            <atom:content src="{$media-uri}" type="{$media-type}"/>
            <atom:title type="text">{$title}</atom:title>
            <atom:summary type="text">{$summary}</atom:summary>
            { $revision-comment }
        </atom:entry>  
     
};




declare function adb:update-entry( 
    $entry as element(atom:entry) ,
    $request-data as element(atom:entry) ,
    $comment as xs:string?
) as element(atom:entry) 
{

    (: TODO validate input data :)
    
    let $updated := current-dateTime()
    let $log := util:log( "debug" , concat( "$updated: " , $updated ) )

    let $user-name := request:get-attribute( $config:user-name-request-attribute-key )
    
    let $revision-comment := 
        <ar:comment>
            <atom:author>
                {
                    if ( $config:user-name-is-email ) then
                    <atom:email>{$user-name}</atom:email>
                    else
                    <atom:name>{$user-name}</atom:name>                    
                }
                <atom:updated>{$updated}</atom:updated>
                <atom:summary>{$comment}</atom:summary>
            </atom:author>
        </ar:comment>
        
    return
    
        <atom:entry>
            {
                $entry/atom:id ,
                $entry/atom:published 
            }
            <atom:updated>{$updated}</atom:updated>
            {
                $entry/atom:link[@rel="self"] ,
                $entry/atom:link[@rel="edit"] ,
                $entry/atom:link[@rel="edit-media"] ,
                $entry/atom:link[@rel="history"] ,
                $entry/ar:comment ,
                $revision-comment ,
                adb:mutable-entry-children($request-data)
            }
        </atom:entry>  
};



declare function adb:create-media-resource(
	$request-path-info as xs:string , 
	$request-data as xs:base64Binary , 
	$request-content-type as xs:string ,
	$media-link-title as xs:string? ,
	$media-link-summary as xs:string? ,
	$comment as xs:string?
) as xs:string
{

	let $media-type := text:groups( $request-content-type , "^([^;]+)" )[2]
	
	let $collection-db-path := adb:request-path-info-to-db-path( $request-path-info )

    let $uuid := util:uuid()
    
	let $media-resource-name := concat( $uuid , ".media" )

	let $media-resource-db-path := xmldb:store( $collection-db-path , $media-resource-name , $request-data , $media-type )
	
    let $media-link-entry := adb:create-media-link-entry( $request-path-info, $uuid , $media-type , $media-link-title , $media-link-summary , $comment )
    
    let $media-link-entry-doc-db-path := xmldb:store( $collection-db-path , concat( $uuid , ".atom" ) , $media-link-entry )    
    
    return $media-link-entry-doc-db-path
	 
};




declare function adb:update-media-resource(
	$request-path-info as xs:string , 
	$request-data as xs:base64Binary , 
	$request-content-type as xs:string
) as xs:string
{

	if ( not( adb:media-resource-available( $request-path-info ) ) )
	
	then ()
	
	else

		let $media-type := text:groups( $request-content-type , "^([^;]+)" )[2]
	
		let $groups := text:groups( $request-path-info , "^(.*)/([^/]+)$" )
		
		let $collection-db-path := adb:request-path-info-to-db-path( $groups[2] )
		
		let $media-doc-name := $groups[3]

		let $media-doc-db-path := xmldb:store( $collection-db-path , $media-doc-name , $request-data , $media-type )
	
		return $media-doc-db-path
};




declare function adb:retrieve-feed(
	$request-path-info as xs:string 
) as element(atom:feed)
{
	
	if ( not( adb:collection-available( $request-path-info ) ) )
	
	then ()
	
	else
	
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $db-collection-path := adb:request-path-info-to-db-path( $request-path-info )
	
		(:
		 : Obtain the database path for the atom feed document in the given database
		 : collection. Currently, this appends ".feed" to the database collection
		 : path.
		 :)
		 
		let $feed-doc-db-path := adb:feed-doc-db-path( $db-collection-path )
		let $feed := doc( $feed-doc-db-path )/atom:feed
		
		return
		
			<atom:feed>	
			{
				$feed/*
			}
			{
				for $child in xmldb:get-child-resources( $db-collection-path )
				let $is-entry-doc := ( not( ends-with( $child, ".media" ) ) and not( ends-with( $child , ".feed" ) ) )
				let $entry := if ( $is-entry-doc ) then doc( concat( $db-collection-path , "/" , $child ) )/atom:entry else ()
				order by $entry/atom:updated descending
				return $entry
			}
			</atom:feed>

};




declare function adb:retrieve-entry(
	$request-path-info as xs:string 
) as element(atom:entry)
{

	if ( not( adb:member-available( $request-path-info ) ) )
	
	then ()
	
	else
	
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $entry-doc-db-path := adb:request-path-info-to-db-path( $request-path-info )
		
		return doc( $entry-doc-db-path )/atom:entry

};




declare function adb:retrieve-media(
	$request-path-info as xs:string 
) as item()*
{

	if ( not( adb:media-resource-available( $request-path-info ) ) )
	
	then ()
	
	else
	
		(:
		 : Map the request path info, e.g., "/foo", to a database collection path,
		 : e.g., "/db/foo".
		 :)
		 
		let $media-doc-db-path := adb:request-path-info-to-db-path( $request-path-info )
		
		return util:binary-doc( $media-doc-db-path )

};




declare function adb:get-mime-type(
	$request-path-info as xs:string 
) as xs:string
{

	let $media-doc-db-path := adb:request-path-info-to-db-path( $request-path-info )

	return xmldb:get-mime-type( xs:anyURI( $media-doc-db-path ) )
	
};



declare function adb:get-media-link(
	$request-path-info as xs:string
) as element(atom:entry)
{

	(: assume path info identifies a media resource :)
	
	let $media-doc-db-path := adb:request-path-info-to-db-path( $request-path-info )
	let $media-link-doc-db-path := replace( $media-doc-db-path , "^(.*)\.media$" , "$1.atom" )
	return doc( $media-link-doc-db-path )/atom:entry

};