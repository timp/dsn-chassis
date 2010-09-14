(: 
    A script implementing a simple REST service to query and retrieve entries
    in the datafiles collection.
:)


(: namespace declarations :)

declare namespace exist = "http://exist.sourceforge.net/NS/exist" ;
declare namespace request = "http://exist-db.org/xquery/request" ;
declare namespace atom = "http://www.w3.org/2005/Atom" ;


import module namespace chassis = "http://www.cggh.org/2009/chassis/xquery-functions" at "chassis-functions.xqm" ;


(: serialization options :)

declare option exist:serialize "method=xml media-type=application/xml indent=yes" ;


(: function declarations :)



declare function local:expand-datafile(
    $entry as element(atom:entry)
    ) as element(atom:entry) 
{

    let $spec :=
        <spec>
            <expand rel="chassis.revision" collection="/db/media"/>
            <expand-reverse rel="chassis.dataset" rev="chassis.datafile" collection="/db/datasets"/>
        </spec>
        
    return chassis:recursive-expand-entry($entry, $spec)
    
};




(: find collection :) 

let $datafiles := collection("/db/datafiles")

(: fish out request params :)
let $param-authoremail := request:get-parameter("authoremail","")
let $param-id := request:get-parameter("id","")

(: return an Atom feed document :)
return 
<atom:feed>
	<atom:title>Query Results</atom:title>
	{
		(: for all Atom entries within the collection :)
		for $e in ($datafiles//atom:entry)
		
		where 
		
		(: filter by author email, if request parameter is given :)
		
		( ($param-authoremail != "" and $e/atom:author/atom:email = $param-authoremail) or ($param-authoremail = "") )

		and
		
		(: filter by ID, if request parameter is given :)

		( ($param-id != "" and $e/atom:id = $param-id) or ($param-id = "") )
			
		(: order by most recently updated :)

		order by $e/atom:updated descending
		
		return local:expand-datafile($e)
	}
</atom:feed>