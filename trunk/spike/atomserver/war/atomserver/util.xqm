xquery version "1.0";

module namespace utilx = "http://www.cggh.org/2010/xquery/util";




declare function utilx:get-or-create-collection(
	$collection-path as xs:string 
) as xs:string?
{
	
	let $log := util:log( "debug" , concat( "$collection-path: " , $collection-path ) )
	 
	let $available := xmldb:collection-available( $collection-path )
	let $log := util:log( "debug" , concat( "$available: " , $available ) )
	 
	return 
		
		if ( $available ) then $collection-path
		
		else 
		
			let $groups := text:groups( $collection-path , "^(.*)/([^/]+)$" )
			let $log := util:log( "debug" , concat( "$groups: " , count( $groups ) ) )
			
			let $target-collection-uri := $groups[2]
			let $log := util:log( "debug" , concat( "$target-collection-uri: " , $target-collection-uri ) )
			
			let $new-collection := $groups[3]
			let $log := util:log( "debug" , concat( "$new-collection: " , $new-collection ) )

			let $target-collection-uri := utilx:get-or-create-collection( $target-collection-uri )
			
            return xmldb:create-collection( $target-collection-uri , $new-collection )
			
};
