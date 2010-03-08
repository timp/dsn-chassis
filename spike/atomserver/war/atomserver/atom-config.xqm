xquery version "1.0";

module namespace config = "http://www.cggh.org/2010/xquery/atom-config";


(:
 : The base URL for the Atom service. This URL will be prepended to all edit
 : and self link href values.
 :)
declare variable $config:service-url as xs:string := "http://localhost:8081/atomserver/atomserver/edit" ;


(:
 : The base URL for the History service. This URL will be prepended to all 
 : history link href values.
 :)
declare variable $config:history-service-url as xs:string := "http://localhost:8081/atomserver/atomserver/history" ;
 

(:
 : The name of the request attribute where the currently authenticated user
 : is stored. N.B. it is assumed that authentication will have been carried out
 : prior to the Atom service handling the request, e.g., by a filter, and that
 : the filter will have stored the authenticated user's name (ID) in the given
 : request attribute.
 :)
declare variable $config:user-name-request-attribute-key as xs:string := "user-name" ; 


(:
 : The name of the request attribute where the currently authenticated user's
 : roles are stored. N.B. it is assumed that authentication and retrieval of
 : the authenticated user's roles will have been carried outprior to the Atom 
 : service handling the request, e.g., by a filter, and that the filter will 
 : have stored the authenticated user's roles in the given request attribute.
 : TODO explain expected type of attribute value.
 :)
declare variable $config:user-roles-request-attribute-key as xs:string := "user-roles" ; 


declare variable $config:user-name-is-email as xs:boolean := false() ;

declare variable $config:base-collection-path as xs:string := "/db" ;

declare variable $config:feed-doc-name as xs:string := ".feed" ;

