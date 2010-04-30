xquery version "1.0";

module namespace rest = "http://atombeat.org/xquery/rest-util";

import module namespace CONSTANT = "http://atombeat.org/xquery/constants" at "../../atombeat/lib/constants.xqm" ;
import module namespace config = "http://atombeat.org/xquery/config" at "../config/shared.xqm" ;




declare function rest:do-not-found(
    $request-path-info
) as item()?
{

    let $message := "The server has not found anything matching the Request-URI."
    
    return rest:send-error( $CONSTANT:STATUS-CLIENT-ERROR-NOT-FOUND , $message , $request-path-info )

};



declare function rest:do-bad-request(
    $request-path-info as xs:string ,
    $message as xs:string 
) as item()?
{

    let $message := concat( $message , " The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications." )

    return rest:send-error( $CONSTANT:STATUS-CLIENT-ERROR-BAD-REQUEST , $message , $request-path-info )

};




declare function rest:do-method-not-allowed(
    $request-path-info
) as item()?
{

    rest:do-method-not-allowed( $request-path-info , ( "GET" , "POST" , "PUT" ) )
    
};




declare function rest:do-method-not-allowed(
    $request-path-info as xs:string ,
    $allow as xs:string*
) as item()?
{

    let $message := "The method specified in the Request-Line is not allowed for the resource identified by the Request-URI."

    let $header-allow := response:set-header( $CONSTANT:HEADER-ALLOW , string-join( $allow , " " ) )

    return rest:send-error( $CONSTANT:STATUS-CLIENT-ERROR-METHOD-NOT-ALLOWED , $message , $request-path-info )

};

 



declare function rest:do-forbidden(
    $request-path-info as xs:string
) as item()?
{

    let $message := "The server understood the request, but is refusing to fulfill it. Authorization will not help and the request SHOULD NOT be repeated."

    return rest:send-error( $CONSTANT:STATUS-CLIENT-ERROR-FORBIDDEN , $message , $request-path-info )

};





declare function rest:send-atom(
    $status as xs:integer ,
    $data as item()
) as item()*
{

    let $status-code := response:set-status-code( $status )
    
    let $header-content-type := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , $CONSTANT:MEDIA-TYPE-ATOM )
    
    return $data

};



declare function rest:send-response(
    $status as xs:integer? ,
    $data as item()? ,
    $content-type as xs:string?
) as item()*
{

    if ( $status >= 400 and $status < 600 )
    
    then (: override to wrap response with useful debugging information :)
        let $request-path-info := request:get-attribute( $rest:param-request-path-info )
        return rest:send-error( $status , $data , $request-path-info )
        
    else
    
        let $status-code-set := 
            if ( exists( $status ) ) then response:set-status-code( $status )
            else ()

        let $header-content-type := 
            if ( exists( $content-type ) ) then response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , $content-type )
            else ()
            
        return $data

};





 
(:
 : TODO doc me
 :)
declare function rest:send-error(
    $status-code as xs:integer , 
    $content as item()? ,
    $request-path-info as xs:string?
) as item()*
{

    let $status-code-set := response:set-status-code( $status-code )

    let $header-content-type := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , $CONSTANT:MEDIA-TYPE-XML )

    let $response := 
    
        <error>
            <status>{$status-code}</status>
            <content>{$content}</content>
            <method>{request:get-method()}</method>
            <path-info>{$request-path-info}</path-info>
            <parameters>
            {
                for $parameter-name in request:get-parameter-names()
                return
                    <parameter>
                        <name>{$parameter-name}</name>
                        <value>{request:get-parameter( $parameter-name , "" )}</value>                      
                    </parameter>
            }
            </parameters>
            <headers>
            {
                for $header-name in request:get-header-names()
                return
                    <header>
                        <name>{$header-name}</name>
                        <value>{request:get-header( $header-name )}</value>                     
                    </header>
            }
            </headers>
            <user>{request:get-attribute($config:user-name-request-attribute-key)}</user>
            <roles>{string-join(request:get-attribute($config:user-roles-request-attribute-key), " ")}</roles>
        </error>
            
    return $response

};



