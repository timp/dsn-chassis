xquery version "1.0";

import module namespace request="http://exist-db.org/xquery/request";
import module namespace xdb = "http://exist-db.org/xquery/xmldb";

<dispatch xmlns="http://exist.sourceforge.net/NS/exist">
    <forward url="/atomserver/index.xql">
        <add-parameter name="path" value="{$exist:path}"/>
    </forward>
</dispatch>

