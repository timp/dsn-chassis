xquery version "1.0";

module namespace example-plugin = "http://www.cggh.org/2010/xquery/atombeat/example-plugin";
declare namespace atom = "http://www.w3.org/2005/Atom" ;
import module namespace util = "http://exist-db.org/xquery/util" ;




declare function example-plugin:before-create-member-1(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as xs:integer
{
	let $message := "before-create-member-1" 
	let $log := util:log( "debug" , $message )
	return 200
};



declare function example-plugin:before-create-member-2(
	$request-path-info as xs:string ,
	$request-data as element(atom:entry)
) as xs:integer
{
	let $message := "before-create-member-2" 
	let $log := util:log( "debug" , $message )
	return 500
};
