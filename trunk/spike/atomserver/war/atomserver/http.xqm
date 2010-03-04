xquery version "1.0";

module namespace http = "http://www.cggh.org/2010/xquery/http";

declare variable $http:method-get as xs:string 						:= "GET" ;
declare variable $http:method-post as xs:string 					:= "POST" ;
declare variable $http:method-put as xs:string 						:= "PUT" ;
declare variable $http:method-delete as xs:string 					:= "DELETE" ;

declare variable $http:status-success-ok as xs:integer 						:= 200 ;
declare variable $http:status-success-created as xs:integer 				:= 201 ;
declare variable $http:status-success-no-content as xs:integer 				:= 204 ;

declare variable $http:status-client-error-bad-request as xs:integer 		:= 400 ;
declare variable $http:status-client-error-not-found as xs:integer 			:= 404 ;
declare variable $http:status-client-error-method-not-allowed as xs:integer := 405 ;

declare variable $http:header-allow as xs:string 					:= "Allow" ;
declare variable $http:header-accept as xs:string 					:= "Accept" ;
declare variable $http:header-content-type as xs:string 			:= "Content-Type" ;
declare variable $http:header-content-disposition as xs:string 		:= "Content-Disposition" ;
declare variable $http:header-location as xs:string 				:= "Location" ;
declare variable $http:header-slug as xs:string		 				:= "Slug" ;

declare variable $http:media-type-multipart-form-data as xs:string	:= "multipart/form-data" ;
