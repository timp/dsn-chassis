let $doc1 := <atom:entry><title>test 1</title><a>A</a></atom:entry>

let $store1 := xmldb:store("/db/test", "test.xml", $doc1)
let $returndoc1 := doc("/db/test/test.xml")/atom:entry
let $returndocroot1 := doc("/db/test/test.xml")
let $child1 := doc("/db/test/test.xml")/atom:entry/a

return
<results>
    <store1>{$store1}</store1>
    <doc1>{$returndoc1}</doc1>
    <doc1root>{$returndocroot1}</doc1root>
    <realdoc1>{$doc1}</realdoc1>
    <child1>{$child1}</child1>
</results>
