xquery version "1.0";

import module namespace request="http://exist-db.org/xquery/request";
import module namespace text="http://exist-db.org/xquery/text";
import module namespace xmldb = "http://exist-db.org/xquery/xmldb";

if ( $exist:path = "/datawiki/merge" ) then

	<dispatch xmlns="http://exist.sourceforge.net/NS/exist">
	    <forward url="/query/datawiki/merge.xql">
	    </forward>
	</dispatch>

else 

	<ignore xmlns="http://exist.sourceforge.net/NS/exist">
        <cache-control cache="yes"/>
	</ignore>
