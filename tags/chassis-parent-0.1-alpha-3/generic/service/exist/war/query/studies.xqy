declare namespace atom="http://www.w3.org/2005/Atom";
declare option exist:serialize "method=xml media-type=application/atom+xml indent=yes";
<atom:feed>
{
for $entry in collection("/db/studies")/atom:entry
where $entry/atom:author/atom:email='alice@example.com'
return $entry
}
</atom:feed>