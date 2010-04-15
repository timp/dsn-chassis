let $returndoc := doc("/db/test/test.xml")/atom:entry
let $returndocroot := doc("/db/test/test.xml")
let $child := doc("/db/test/test.xml")/atom:entry/a

return
<results>
    <doc>{$returndoc}</doc>
    <docroot>{$returndocroot}</docroot>
    <child>{$child}</child>
</results>
