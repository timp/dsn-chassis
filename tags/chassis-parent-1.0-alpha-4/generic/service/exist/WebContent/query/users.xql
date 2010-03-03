declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

(: serialization options :)
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;

let $type-scheme-uri := "http://www.cggh.org/2009/chassis/type/"
return
<atom:feed>
    <atom:title>Query Results</atom:title>
    <atom:entry>
        <atom:id>jane@example.org</atom:id>
        <atom:category scheme="{$type-scheme-uri}" term="User"/>
    </atom:entry>
    <atom:entry>
        <atom:id>fred@example.org</atom:id>
        <atom:category scheme="{$type-scheme-uri}" term="User"/>
    </atom:entry>
</atom:feed>