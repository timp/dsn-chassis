(: 
    A script implementing a simple REST service to query and retrieve entries
    in the datasets collection.
:)


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;


import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "chassis-functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: function declarations :)



declare function local:expand-dataset(
    $entry as element(atom:entry)
    ) as element(atom:entry) 
{

    let $spec :=
        <spec>
            <expand rel="chassis.study" collection="/db/studies"/>
            <expand rel="chassis.datafile" collection="/db/datafiles">
                <spec>
                    <expand rel="chassis.revision" collection="/db/media"/>
                </spec>
            </expand>
            <expand-reverse rel="chassis.submission" rev="chassis.dataset" collection="/db/submissions">
                <spec>
                    <expand-reverse rel="chassis.review" rev="chassis.submission" collection="/db/reviews"/>
                </spec>
            </expand-reverse>
        </spec>
        
    return chassis:recursive-expand-entry($entry, $spec)
    
};


(: find collection :) 

let $datasets := collection("/db/datasets")

(: fish out request params :)

let $param-authoremail := request:get-parameter("authoremail","")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($datasets//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		
		( ($param-authoremail != "" and $e/atom:author/atom:email = $param-authoremail) or ($param-authoremail = "") )

		and
		
		(: filter by ID, if request parameter is given :)

		( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
		(: order by most recently updated :)

		order by $e/atom:updated descending
		
		return local:expand-dataset($e)
	}
</atom:feed>