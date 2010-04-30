xquery version "1.0";




declare variable $local:logger-name := "org.cggh.chassis.datawiki.merge" ;




declare function local:debug(
    $message as item()*
) as empty()
{
    util:log-app( "debug" , $local:logger-name , $message )
};




declare function local:info(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $local:logger-name , $message )
};




declare function local:do-service() as item()*
{

    let $request-path-info := request:get-attribute( $ap:param-request-path-info )
    let $request-method := request:get-method()
    
    return
    
        if ( $request-method = $CONSTANT:METHOD-POST )

        then local:do-post( $request-path-info )
        
        else local:do-method-not-allowed( $request-path-info )
    
};




let $login := xmldb:login( "/" , "admin" , "" )

return local:do-service()