declare namespace atom="http://www.w3.org/2005/Atom";
declare option exist:serialize "method=xml media-type=application/atom+xml indent=yes";
<atom:feed 
xmlns:atom="http://www.w3.org/2005/Atom" 
xmlns:chassis="http://www.cggh.org/2009/chassis/atom/xmlns"> 
{ 
let $studies := collection("/db/studies")//atom:feed
return
(
	$studies/atom:id, 
	$studies/atom:title, 
	$studies/atom:updated, 
	for $e in ($studies/atom:entry) 
		where $e/atom:author/atom:email = 'alice@example.com' 
		return $e 
)
} 
</atom:feed> 

