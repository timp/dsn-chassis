declare namespace atom="http://www.w3.org/2005/Atom";
<atom:feed>
{
let $base-uri := base-uri(/atom:feed)
let $substring-before := substring-before($base-uri,'/.feed.atom')
let $string-join := string-join(($substring-before,".feed.entry"),"/")
let $base := collection($string-join)
$base/atom:entry[atom:author/atom:email='alice@example.com']
}
</atom:feed>