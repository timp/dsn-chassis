xquery version "1.0";

import module namespace request="http://exist-db.org/xquery/request";
import module namespace xdb = "http://exist-db.org/xquery/xmldb";

let $groups := text:groups( $exist:path , "^/([^/]+)(.*)$" )
let $module := $groups[2]
let $request-path-info := $groups[3]

return

	if ( $module = "edit" ) then

		<dispatch xmlns="http://exist.sourceforge.net/NS/exist">
		    <forward url="/atomserver/edit.xql">
		        <add-parameter name="request-path-info" value="{$request-path-info}"/>
		    </forward>
		</dispatch>

	else 

		<ignore xmlns="http://exist.sourceforge.net/NS/exist">
            <cache-control cache="yes"/>
		</ignore>
	