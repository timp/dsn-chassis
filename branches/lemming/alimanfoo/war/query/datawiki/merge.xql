xquery version "1.0";

import module namespace CONSTANT = "http://atombeat.org/xquery/constants" at "../../atombeat/lib/constants.xqm" ;
import module namespace rest = "http://atombeat.org/xquery/rest-util" at "../../atombeat/lib/rest-util.xqm" ;
import module namespace config = "http://atombeat.org/xquery/config" at "../../atombeat/config/shared.xqm" ;

declare namespace atom = "http://www.w3.org/2005/Atom" ;

declare variable $local:logger-name := "org.cggh.chassis.datawiki.merge" ;




declare function local:debug(
    $message as item()*
) as empty()
{
    util:log-app( "debug" , $local:logger-name , $message )
};




declare function local:info(
    $message as item()*
) as empty()
{
    util:log-app( "info" , $local:logger-name , $message )
};




declare function local:do-service() as item()*
{

    let $request-method := request:get-method()
    
    return
    
        if ( $request-method = $CONSTANT:METHOD-POST )

        then local:do-post()
        
        else rest:do-method-not-allowed( "/query/datawiki/merge.xql" , ( "POST") )
    
};



declare function local:do-post() as item()*
{
    let $data := request:get-data()
    let $merge := $data/atom:content/merge
    let $status-code-set := response:set-status-code( $CONSTANT:STATUS-SUCCESS-OK )
    let $content-type-set := response:set-header( $CONSTANT:HEADER-CONTENT-TYPE , $CONSTANT:MEDIA-TYPE-ATOM )
    return 
        <atom:entry>
        {
            $data/atom:title
        }
            <atom:content type="application/vnd.chassis-datawiki+xml">
                <merge xmlns="">
                {
                    $merge/design
                }
                </merge>
            {
                local:calculate-merge-table($merge/design)
            }
            </atom:content>
        </atom:entry>
};




declare function local:calculate-merge-table(
    $design as element()
) as item()*
{
    let $source1-path := concat( $config:base-collection-path , substring-after( $design/source[1] , $config:service-url ) )
    let $source1-doc := doc( $source1-path )
    let $source1-join-index := 
        for $p in $source1-doc//thead//th/position() return
            if ( $source1-doc//thead//th[$p]/label = $design/join/label ) then $p else ()
                                 
    let $source2-path := concat( $config:base-collection-path , substring-after( $design/source[2] , $config:service-url ) )
    let $source2-doc := doc( $source2-path )
    let $source2-join-index := 
        for $p in $source2-doc//thead//th/position() return
            if ( $source2-doc//thead//th[$p]/label = $design/join/label ) then $p else ()

    return 
        <table>
            <thead>
                <source1-join-index>{$source1-join-index}</source1-join-index>
                <tr>
                    (: output join column header :)
                    <th>
                    {
                        $design/join/label ,
                        $design/join/datatype ,
                        $design/join/required
                    }
                    </th>
                {
                    (: output merge column headers :)
                    for $merge in $design/merges/merge
                    return
                        <th>
                        {
                            $merge/label , 
                            $merge/datatype , 
                            $merge/required
                        }    
                        </th> 
                    ,
                    (: output copy column headers :)
                    for $col in $design/copy/col
                    let $source-th := 
                        if ($col/source = '1') then $source1-doc//thead//th[label=$col/label]
                        else $source2-doc//thead//th[label=$col/label]
                    return
                        <th>
                            <label>{$col/rename/text()}</label>
                        {   
                            $source-th/datatype ,
                            $source-th/required
                        }
                        </th>
                }
                </tr>
            </thead>
            <tbody>
            {
                for $source1-tr in $source1-doc//tbody/tr
                let $source1-key := $source1-tr/td[$source1-join-index]/text()
                let $source2-tr :=
                    $source2-doc//tbody/tr[td[$source2-join-index]/text() = $source1-key]
                let $source2-key := $source2-tr/td[$source2-join-index]/text()
                return 
                    <tr>
                        <!-- output join column -->
                        <td>
                            <source>{$source1-key}</source>
                            <source>{$source2-key}</source>
                            <decision>
                            {
                                if ( empty( $source1-key) and exists( $source2-key) ) then $source2-key else $source1-key
                            }
                            </decision>
                        </td>
                        <!-- output merge columns -->
                    {
                        for $merge in $design/merges/merge
                        let $source1-index :=
                            for $p in $source1-doc//thead//th/position() return
                                if ( $source1-doc//thead//th[$p]/label = $merge/col[1]/label ) then $p else ()
                        let $source1-value := $source1-tr/td[$source1-index]/text()
                        let $source2-index :=
                            for $p in $source2-doc//thead//th/position() return
                                if ( $source2-doc//thead//th[$p]/label = $merge/col[2]/label ) then $p else ()
                        let $source2-value := $source2-tr/td[$source2-index]/text()
                        return
                            <td>
                                <source>{$source1-value}</source>
                                <source>{$source2-value}</source>
                                <decision>
                                {
                                    if ( empty( $source1-value ) and exists( $source2-value ) ) then $source2-value 
                                    else if ( empty( $source2-value ) and exists( $source1-value ) ) then $source1-value 
                                    else if ( $source1-value = $source2-value ) then $source1-value
                                    else ()
                                }
                                </decision>
                            </td>
                        ,
                        
                        for $col in $design/copy/col
                        let $index := 
                            if ($col/source = '1') then
                                for $p in $source1-doc//thead//th/position() return
                                    if ( $source1-doc//thead//th[$p]/label = $col/label ) then $p else ()
                            else 
                                for $p in $source2-doc//thead//th/position() return
                                    if ( $source2-doc//thead//th[$p]/label = $col/label ) then $p else ()
                        let $value :=
                            if ($col/source = '1') then
                                $source1-tr/td[$index]/text()
                            else                            
                                $source2-tr/td[$index]/text()
                        return
                            <td>
                                <source>{if ($col/source='1') then $value else ()}</source>
                                <source>{if ($col/source='2') then $value else ()}</source>
                                <decision>{$value}</decision>
                            </td>
                                    
                        
                    }
                        
                    </tr>
                ,
                for $source2-tr in $source2-doc//tbody/tr
                let $source2-key := $source2-tr/td[$source2-join-index]/text()
                where empty($source1-doc//tbody/tr[td[$source1-join-index]/text() = $source2-key])
                return
                    <tr>
                        <!-- output join column -->
                        <td>
                            <source></source>
                            <source>{$source2-key}</source>
                            <decision>{$source2-key}</decision>
                        </td>
                        <!-- output merge columns -->
                    {
                        for $merge in $design/merges/merge
                        let $source2-index :=
                            for $p in $source2-doc//thead//th/position() return
                                if ( $source2-doc//thead//th[$p]/label = $merge/col[2]/label ) then $p else ()
                        let $source2-value := $source2-tr/td[$source2-index]/text()
                        return
                            <td>
                                <source></source>
                                <source>{$source2-value}</source>
                                <decision>{$source2-value}</decision>
                            </td>
                        ,
                        for $col in $design/copy/col
                        let $index := 
                            if ($col/source='2') then
                                for $p in $source2-doc//thead//th/position() return
                                    if ( $source2-doc//thead//th[$p]/label = $col/label ) then $p else ()
                            else ()
                        let $value :=
                            if ($col/source='2') then
                                $source2-tr/td[$index]/text()
                            else ()
                        return
                            <td>
                                <source></source>
                                <source>{$value}</source>
                                <decision>{$value}</decision>
                            </td>
                                    
                    }
                        
                    </tr>
                
                
            }
            </tbody>
        </table>
};



let $login := xmldb:login( "/" , "admin" , "" )

return local:do-service()