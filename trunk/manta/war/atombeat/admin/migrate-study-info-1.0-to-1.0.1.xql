declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace atombeat = "http://purl.org/atombeat/xmlns" ;

import module namespace response = "http://exist-db.org/xquery/response" ;

import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;
import module namespace CONSTANT = "http://purl.org/atombeat/xquery/constants" at "../lib/constants.xqm" ;
import module namespace xutil = "http://purl.org/atombeat/xquery/xutil" at "../lib/xutil.xqm" ;
import module namespace atomsec = "http://purl.org/atombeat/xquery/atom-security" at "../lib/atom-security.xqm" ;
import module namespace atomdb = "http://purl.org/atombeat/xquery/atomdb" at "../lib/atomdb.xqm" ;
import module namespace ap = "http://purl.org/atombeat/xquery/atom-protocol" at "../lib/atom-protocol.xqm" ;
import module namespace common-protocol = "http://purl.org/atombeat/xquery/common-protocol" at "../lib/common-protocol.xqm" ;
import module namespace config-collections = "http://purl.org/atombeat/xquery/config-collections" at "collections.xqm" ;

(: Migration actions: :)
(: Adds "co2Other", "o2" and "o2Other" into the "culture" element in "invitro", after "co2". :)
(: Adds "platePreparationMethod" and "platesPreparationDate" into "invitro", after "drugs". :)
(: If there are batches in the "plateBatches" element of "invitro", then gives the content "batches" to the "platePreparationMethod" element. :)


declare function local:do-get() as item()*
{
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};



declare function local:content() as item()*
{
    let $study-infos := atomdb:retrieve-members( "/study-info" , false() )
    let $study-infos-v1-0 := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    let $study-infos-v1-0-1 := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0.1']
    
    return
    
        <html>
            <head>
                <title>Data Migration - Study Info v1.0 to v1.0.1</title>
            </head>
            <body>
                <h1>Data Migration - Study Info v1.0 to v1.0.1</h1>
                <p>Total number of entries in <a href="../content/study-info">Study Info</a> collection: <strong>{ count( $study-infos ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0 profile: <strong>{ count( $study-infos-v1-0 ) }</strong></p>
                <p>Number of entries in <a href="../content/study-info">Study Info</a> collection using v1.0.1 profile: <strong>{ count( $study-infos-v1-0-1 ) }</strong></p>
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



declare function local:do-post() as item()*
{

    let $study-infos := atomdb:retrieve-members( "/study-info" , false() )
    let $study-infos-v1-0 := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    
    let $new := local:add-new-nodes()
    
    let $study-infos-v1-0-1 := 
        for $v1-0 in $study-infos-v1-0
        return 
            <atom:entry>
            {
                $v1-0/attribute::* ,
                for $e in $v1-0/child::* 
                return
                    if (
                        local-name( $e ) = $CONSTANT:ATOM-CONTENT
                        and namespace-uri( $e ) = $CONSTANT:ATOM-NSURI
                    )
                    then 
                        <atom:content>
                        {
                            $e/attribute::* ,
                            local:migrate-study-info( $e/study-info )
                        }
                        </atom:content>
                    else $e
            }
            </atom:entry>
            
    let $migrated :=     
        for $v1-0-1 in $study-infos-v1-0-1 
        let $path-info := atomdb:edit-path-info( $v1-0-1 )
        return atomdb:update-member( $path-info , $v1-0-1 )
    
    (: SEND RESPONSE :)        
    let $response-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $response-content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , "text/html" )
    return local:content()
};


declare function local:add-new-nodes() 
{
    let $study-infos := atomdb:retrieve-members( "/study-info" , false() )
    let $study-infos-v1-0 := $study-infos[atom:content/study-info/@profile='http://www.cggh.org/2010/chassis/manta/1.0']
    
    let $study-infos-v1-0-1 := 
        for $v1-0 in $study-infos-v1-0
            let $ren := update rename //wGroupDosing as '/weightGroupDosing'
            let $ren1 := update rename //wGroupDosingSchedule as '/weightGroupDosingSchedule' 
            let $ren2 := update rename //wGroupFrom as '/weightGroupFrom' 
            let $ren3 := update rename //wGroupTo as '/weightGroupTo'
            let $ren4 := update rename //co2 as '/co2percentage'
            let $ren5 := update rename //hematocrit as '/hematocritpercentage'
            let $del := update delete //clinical/recrudescenceAndReinfection
            let $del1 := update delete //clinical/inclusionCriteria
            let $del2 := //pharmacology/samples/sample/numberPlanned
            let $del3 := //pharmacology/analytes/analyte[/drugMeasured/text()='AL']
            let $rep1 := update replace //drugMeasured[. = "SP"] with <drugMeasured>SX</drugMeasured>
            let $rep2 := update replace //drugMeasured[. = "PPQ"] with <drugMeasured>PQ</drugMeasured>
            return update insert <studyInfoStatus>new</studyInfoStatus> preceding //start

     
    
     return for $v1-0-1 in $study-infos-v1-0-1 
        let $path-info := atomdb:edit-path-info( $v1-0-1 )
        return atomdb:update-member( $path-info , $v1-0-1 )

    
};

declare function local:migrate-study-info( $study-info as element( study-info ) ) as element( study-info )
{
    <study-info profile="http://www.cggh.org/2010/chassis/manta/1.0.1">
    {
        for $study-info-child in $study-info/child::*
        return
            if ( local-name( $study-info-child ) = "invitro" )
            then
                <invitro>
                {
                    $study-info-child/attribute::* ,
                    for $invitro-child in $study-info-child/child::*
                    let $platePreparationMethod-element :=
                        if ( $study-info-child/plateBatches[not(node())] )
                        then <platePreparationMethod/>
                        else <platePreparationMethod>batches</platePreparationMethod>
                    let $modified-invitro-child-insert-after-drugs := ($invitro-child, $platePreparationMethod-element, <platesPreparationDate/>)
                    return 
                        if ( local-name( $invitro-child ) = "culture" )
                        then
                            <culture>
                            {
                                $invitro-child/attribute::* ,
                                for $culture-child in $invitro-child/child::*
                                let $modified-culture-child-insert-after-co2 := ($culture-child, <co2Other/>, <o2percentage/>, <o2Other/>)
                                return
                                    if ( local-name( $culture-child ) = "co2percentage" )
                                    then $modified-culture-child-insert-after-co2
                                    else $culture-child
                             }
                             </culture>
                        else
                            if ( local-name( $invitro-child ) = "drugs" )
                            then $modified-invitro-child-insert-after-drugs
                            else $invitro-child
                }
                </invitro>
            else $study-info-child
    }
    </study-info>
};



let $login := xmldb:login( "/" , "admin" , "" )

return 

    if ( request:get-method() = $CONSTANT:METHOD-GET ) 
    
    then local:do-get()
    
    else if ( request:get-method() = $CONSTANT:METHOD-POST ) 
    
    then local:do-post()
    
    else common-protocol:do-method-not-allowed( "/admin/migrate-study-info-1.0-to-1.0.1.xql" , ( "GET" , "POST" ) )
    
    

