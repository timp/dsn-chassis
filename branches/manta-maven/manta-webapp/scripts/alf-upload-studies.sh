#alf-upload-studies.sh
if [[ $(uname) == Cygwin ]]
then
 SEPARATOR=';'
else
 SEPARATOR=':'
fi

CLASSPATH=.
for i in `ls jars/*`
do
        CLASSPATH="$CLASSPATH$SEPARATOR./$i"
done
echo classpath ${CLASSPATH}

ALF_MACHINE_ADDRESS=alfresco:8080
#ALF_MACHINE_ADDRESS=129.67.45.244:8080
ALF_HOME=http://${ALF_MACHINE_ADDRESS}/alfresco/service
UPDATE=false # always - update broken
mkdir cmis-entries
mkdir cmis-folders
for j in studies/*
do
	NAME=`echo -n $j| sed -e 's#studies/##'`
	STUDY=`echo -n ${NAME}| sed -e 's#.xml##'`
	#Create the spaces (folders)
	OUT=cmis-folders/${NAME}
	echo "Outputting to $OUT" 
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j -XSL study-cmis.xsl -OUT ${OUT} 
	
	if [ $UPDATE = 'true' ]
	then
		curl -s -uadmin:admin -X PUT -HContent-type:application/atom+xml  --data @${OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}
	else
		curl -s -uadmin:admin -X POST -HContent-type:application/atom+xml  --data @${OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/children
	fi	
	METADATA=cmis-entries/${NAME}
	METADATA_FILE=cmis-entries/${NAME}.load
	#Create the metadata file in Alfresco
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j -XSL study-metadata-cmis.xsl -OUT ${METADATA}
	if [ $UPDATE = 'true' ]
	then
		curl -s -S -uadmin:admin -X PUT -HContent-type:application/atom+xml  --data @${METADATA} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/${NAME}
	else
		curl -s -S -uadmin:admin -X POST -HContent-type:application/atom+xml  --data @${METADATA} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children
	fi
#Create the content file
cat >${METADATA_FILE} <<+++EOH
<?xml version="1.0" encoding="UTF-8"?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:app="http://www.w3.org/2007/app" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:xi="http://www.w3.org/2001/XInclude" xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xforms="http://www.w3.org/2002/xforms" xmlns:alf="http://www.alfresco.org" xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns" xmlns:atombeat="http://purl.org/atombeat/xmlns" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <title>${NAME}</title>
+++EOH
#    echo -n '<content type="application/octet-stream">' >> ${METADATA_FILE}
    echo -n '<content type="text/xml">' >> ${METADATA_FILE}
#	openssl base64 -in $j -out ${METADATA}.base64
#cat ${METADATA}.base64 | tr -d '\n'  >>${METADATA_FILE}
cat $j | sed -e 's#<?xml version="1.0" encoding="UTF-8"?>##' >> ${METADATA_FILE}	
cat >>${METADATA_FILE} <<+++EOT
</content>
    <cmisra:object>
        <cmis:properties>
            <cmis:propertyId propertyDefinitionId="cmis:objectTypeId"><cmis:value>D:wc:studyInfo</cmis:value></cmis:propertyId>
            <cmis:propertyString propertyDefinitionId="cmis:contentStreamMimeType"><cmis:value>text/xml</cmis:value></cmis:propertyString>
        </cmis:properties>
    </cmisra:object>
</entry>
+++EOT
#Update metadata file
	curl -s -S -uadmin:admin -X PUT -HContent-type:application/atom+xml  --data @${METADATA_FILE} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/${NAME}
done

echo "end alf-upload-studies.sh" 
