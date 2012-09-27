let $params :=
<parameters>
<param name="output" value="export"/>
<param name="backup" value="yes"/>
<param name="incremental" value="yes"/>
</parameters>

let $do_backup := system:trigger-system-task("org.exist.storage.ConsistencyCheckTask", $params)
return <response><status>ok</status><msg>Full backup done to export directory export under exist home e.g. /data/atombeat/exist/export</msg></response> 
