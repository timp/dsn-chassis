let $doc2 := <atom:entry><title>test 2</title><b>B</b></atom:entry>

let $store2 := xmldb:store("/db/test", "test.xml", $doc2)
let $returndoc2 := doc("/db/test/test.xml")/atom:entry
let $returndocroot2 := doc("/db/test/test.xml")
let $child2 := doc("/db/test/test.xml")/atom:entry/b

return
<results>
    <store2>{$store2}</store2>
    <doc2>{$returndoc2}</doc2>
    <doc2root>{$returndocroot2}</doc2root>
    <realdoc2>{$doc2}</realdoc2>
    <child2>{$child2}</child2>
</results>
