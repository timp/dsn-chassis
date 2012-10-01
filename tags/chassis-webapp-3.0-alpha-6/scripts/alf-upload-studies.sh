#alf-upload-studies.sh

. ./config.sh

UPDATE=false # always - update broken
mkdir cmis-entries
mkdir cmis-folders
for j in ${STUDIES_DIR}/*
do
	NAME=`echo -n $j| sed -e "s#${STUDIES_DIR}/##"`
	STUDY=`echo -n ${NAME}| sed -e 's#.xml##'`
	#Create the spaces (folders)
	OUT=cmis-folders/${NAME}
	echo "Outputting to $OUT" 
	
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j -XSL study-cmis.xsl -OUT ${OUT} > t.tmp 
	echo "done" 
	if [ $UPDATE = 'true' ]
	then
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X PUT  -HContent-type:application/atom+xml  --data @${OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}
	else
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X POST -HContent-type:application/atom+xml  --data @${OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/children
	fi	
	METADATA=cmis-entries/${NAME}
	METADATA_FILE=cmis-entries/${NAME}.load
	#Create the metadata file in Alfresco
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j -XSL study-metadata-cmis.xsl -OUT ${METADATA}
	if [ $UPDATE = 'true' ]
	then
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X PUT  -HContent-type:application/atom+xml  --data @${METADATA} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/${NAME}
	else
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X POST -HContent-type:application/atom+xml  --data @${METADATA} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children
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
	curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X PUT -HContent-type:application/atom+xml  --data @${METADATA_FILE} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/${NAME}
done

echo "end alf-upload-studies.sh" 


