declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;
declare namespace at = "http://purl.org/atompub/tombstones/1.0";

import module namespace request = "http://exist-db.org/xquery/request" ;
import module namespace response = "http://exist-db.org/xquery/response" ;
import module namespace util = "http://exist-db.org/xquery/util" ;
import module namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;




declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};



declare function local:content() as item()*
{
    let $x := ''
    return
    
        <html>
            <head>
                <title>Remove atom extension</title>
            </head>
            <body>
                <h1>Remove atom extension</h1>
                
                <p>
                    <form method="post" action="">
                        <input type="submit" value="Migrate Data"></input>
                    </form>
                    <form method="get" action="">
                        <input type="submit" value="Refresh"></input>
                    </form>
                </p>
            </body>
        </html>
};

declare function local:after-content() as item()*
{
let $x := ''
return

<html>
    <head>
        <title>Remove atom extension</title>
    </head>
    <body>
        <h1>Remove atom extension</h1>
        
        <p>
            Removed atom extension
        </p>
        <p>
            Tombstones not changed
        </p>
    </body>
</html>
};

declare function local:do-modifications() as element( atom:entry )*
{

   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/originStudy' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')
    
   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='self' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')
    	
   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='edit' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')
   
   let $mod := for $link in collection('/db/atombeat/content')//atom:id[contains(.,'.atom')]
    	return update value $link with substring-before($link/text(),'.atom')
    	
   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/derivationInput' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')
    	
   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/derivationOutput' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')
    	
   let $mod := for $link in collection('/db/atombeat/content')//atom:link[@rel='http://www.cggh.org/2010/chassis/terms/reviewSubject' and contains(@href,'.atom')]
    	return update value $link/@href with substring-before($link/@href,'.atom')    	

(:
      let $mod := local:rename-resources('/studies')
      let $mod := local:rename-resources('/study-info')
      let $mod := local:rename-resources('/drafts') 
      let $mod := local:rename-resources('/media/submitted')
      let $mod := local:rename-resources('/media/curated')
      let $mod := local:rename-resources('/media/draft') 
      let $mod := local:rename-resources('/reviews/personal-data')
      :)
    return $mod  
};

declare function local:rename-resources($coll as xs:string) {
    let $collection := concat('/atombeat/content',$coll)
    let $col-root := concat($config:content-service-url, $coll, '/')
    let $studies := collection( $collection )
    let $mod := for $resource in $studies
        let $atom-id := $resource//atom:id
        let $nn := substring-after($atom-id,$col-root)
        let $new-name := if (contains($nn,'/')) then
                let $ret := substring-after($nn,"/")
                return $ret
              else
                let $ret := $nn
                return $ret
         let $collection-name := if (contains($nn,'/')) then
                let $ret := concat($collection,'/',substring-before($nn,"/"))
                return $ret
              else
                let $ret := $collection
                return $ret
        let $old-name := concat($new-name,'.atom')
        let $debug := util:log-app( "debug" , "migrate-db" ,$atom-id)
        let $debug := util:log-app( "debug" , "migrate-db" ,$collection)
        let $debug := util:log-app( "debug" , "migrate-db" , concat($collection-name,' Renaming:',$old-name,' to ',$new-name ))
        return if (string-length($new-name) > 0) then xmldb:rename($collection-name, $old-name, $new-name)
            else
                ()
        
    return $mod
};


declare function local:do-post() as item()*
{
(: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:after-content()
};


declare function local:do-migration() {

    let $new-content := local:do-modifications()
        
    return local:do-post()
};

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )
(: If this fails with a null pointer exception then you need to reindex the database :)
let $host := doc("atombeat/content/studies/.feed")//atom:link[@rel="self"]/@href
let $current-host := substring-before($host,"/content")
let $new-host := $config:service-url-base

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-migration()
    
    else common-protocol:do-method-not-allowed( "/admin/migrate-db.xql" ,"/admin/migrate-db.xql" , ( "GET" , "POST" ) )
    
    
