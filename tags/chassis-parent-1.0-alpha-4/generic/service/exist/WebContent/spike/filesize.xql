declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace xmldb = "http://exist-db.org/xquery/xmldb" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace math = "http://exist-db.org/xquery/math" ;
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


<out>
    {
        for $entry in collection("/db/media")//atom:entry
        let $resource := xs:string($entry/atom:content/@src)
        let $size := round( xmldb:size("/db/media", $resource) div 1024 )
        return
            <entry>
                <resource>{$resource}</resource>
                <size>
                {
                    if ($size > 1000) then concat(xs:string(round($size div 1024)), "M") else concat(xs:string($size), "K")
                }
                </size>
            </entry>
    }
</out>