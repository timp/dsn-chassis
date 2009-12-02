declare namespace atom="http://www.w3.org/2005/Atom";
declare option exist:serialize "method=xml media-type=application/atom+xml indent=yes";
<atom:feed>
{
let $base := collection("/db/studies")
return $base/atom:entry[atom:author/atom:email='alice@example.com']
}
</atom:feed>