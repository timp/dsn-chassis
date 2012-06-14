xquery version "1.0";
 
            let $start-time := util:system-time()
            let $reindex := xmldb:reindex('/db/atombeat')
            let $end-time := util:system-time()
            let $runtimems := (($end-time - $start-time) div xs:dayTimeDuration('PT1S'))  * 1000 
            return $runtimems
