xquery version "1.0";

module namespace config = "http://www.cggh.org/2010/xquery/atom-config";

declare variable $config:service-url as xs:string := "http://localhost:8081/atomserver/atomserver/edit" ;
declare variable $config:history-service-url as xs:string := "http://localhost:8081/atomserver/atomserver/history" ;

declare variable $config:user-name-request-attribute-key as xs:string := "user-name" ; 
declare variable $config:user-roles-request-attribute-key as xs:string := "user-roles" ; 

declare variable $config:user-name-is-email as xs:boolean := false() ;