(: namespace declarations :)
declare namespace atom = "http://www.w3.org/2005/Atom" ;
declare namespace my = "http://www.cggh.org/2009/chassis/xquery-function" ;



(: serialization options :)
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;



(: declare functions :)

declare function my:link( $entry as element(), $rel as xs:string ) as xs:string* {
	$entry/atom:link[@rel=$rel]/@href
};

declare function my:get-submission-by-url( $url as xs:string ) as element() {
	collection("/db/submissions")//atom:entry[ my:link(., 'edit') = $url ]
};

declare function my:is-linked-from-submission( $study as element() , $submissionUrl as xs:string ) as xs:boolean {

	let $submission := my:get-submission-by-url($submissionUrl)

	return if ( my:link($submission, 'chassis.study') = my:link($study, 'edit') ) then true() else false()

};



(: find collections :)
 
let $studies := collection("/db/studies")


(: fish out request params :)

let $param_authoremail := request:get-parameter("authoremail","")
let $param_submission := request:get-parameter("submission","")



(: return an Atom feed document :)

return 
<atom:feed>
    <params><authoremail>{$param_authoremail}</authoremail><submission>{$param_submission}</submission></params>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		
		for $study in ($studies//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)

		( ( $param_authoremail != "" and $param_authoremail = $study/atom:author/atom:email ) or ( $param_authoremail = "" ) )

		(: filter by submission, if request parameter is given :)
		
		and
		
		( ( $param_submission != "" and my:is-linked-from-submission($study, $param_submission) ) or ( $param_submission = "" ) )

		(: order by most recently updated :)
		
		order by $study/atom:updated descending
		
		return $study
	}
</atom:feed>