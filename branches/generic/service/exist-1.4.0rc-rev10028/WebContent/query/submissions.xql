(: namespace declarations :)
declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;

import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "chassis-functions.xqm" ;



(: serialization options :)
declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;




declare function local:expand-submission( $submissionEntry as element(atom:entry) ) as element(atom:entry) {

    let $expansions := 
        <expand rel="chassis.dataset" collection="/db/datasets">
            <expand rel="chassis.study" collection="/db/studies"/>
            <expand rel="chassis.datafile" collection="/db/datafiles"/>
        </expand>

    return chassis:recursive-expand-entry($submissionEntry, $expansions)
    
};



declare function local:simple-expand-submission( $submissionEntry as element(atom:entry) ) as element(atom:entry) {

    <atom:entry>
    {
        $submissionEntry/atom:title,
        $submissionEntry/atom:summary,
        $submissionEntry/atom:id,
        $submissionEntry/atom:published,
        $submissionEntry/atom:updated,
        $submissionEntry/atom:category,
        $submissionEntry/atom:author,
        $submissionEntry/atom:link[@rel = 'edit'],
        chassis:expand-links($submissionEntry, "chassis.dataset", "/db/datasets")
    }
    </atom:entry>

};



(: find submissions colletion :) 
let $submissions := collection("/db/submissions")



(: fish out request params :)
let $param-authoremail := request:get-parameter("authoremail","")
let $param-acceptancereview := request:get-parameter("acceptancereview", "")





(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($submissions//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		($param-authoremail != "" and $e/atom:author/atom:email = $param-authoremail) or ($param-authoremail = "")

		(: order by most recently updated :)
		order by $e/atom:published descending
		
		return local:expand-submission($e)
	}
</atom:feed>