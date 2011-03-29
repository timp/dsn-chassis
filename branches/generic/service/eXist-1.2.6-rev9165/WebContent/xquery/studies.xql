declare namespace atom="http://www.w3.org/2005/Atom";
declare option exist:serialize "method=xml media-type=application/atom+xml indent=yes";

let $authoremail:= request:get-parameter("authoremail",0)
let $xml := 
<atom:feed 
xmlns:atom="http://www.w3.org/2005/Atom" 
xmlns:chassis="http://www.cggh.org/2009/chassis/atom/xmlns"> 
<atom:title>Query Results - Studies by Author Email ({$authoremail})</atom:title>
{ 
let $studies := collection("/db/studies")//atom:feed
return
(
	for $e in ($studies/atom:entry) 
		where $e/atom:author/atom:email = $authoremail 
		return $e 
)
} 
</atom:feed> 

return $xml