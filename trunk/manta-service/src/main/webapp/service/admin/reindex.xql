xquery version "1.0";
import module namespace config = "http://purl.org/atombeat/xquery/config" at "../config/shared.xqm" ;

let $login := xmldb:login( "/" , $config:exist-user , $config:exist-password )

            let $start-time := util:system-time()
            let $reindex := xmldb:reindex('/db/atombeat')
            let $end-time := util:system-time()
            let $runtimems := (($end-time - $start-time) div xs:dayTimeDuration('PT1S'))  * 1000 
            return concat('Time to reindex in ms:',$runtimems)
