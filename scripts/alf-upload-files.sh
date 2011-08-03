#alf-upload-studies.sh
CLASSPATH=.
for i in `ls jars/*`
do
	CLASSPATH="$CLASSPATH;./$i"
done
ALF_HOME=http://129.67.45.244:8080/alfresco/service
UPDATE=false
DIR=cmis-files
mkdir ${DIR}
for j in submitted/* curated/*
do
	NAME=`echo -n $j| sed -e 's#submitted/##'`
	STUDY=`grep originStudy $j | awk -F\" '{print $10}'`
		
	METADATA=${DIR}/${NAME}
	METADATA_FILE=${DIR}/${NAME}.load
	METADATA_CREAT=${DIR}/${NAME}.creat
	#Create the metadata file in Alfresco
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j -XSL file-cmis.xsl -OUT ${METADATA}

	FILE=`cat $j |grep edit-media | awk -F\" '{print $8}'| awk -F/ '{print $NF}'`
	TYPE=`cat $j |grep edit-media | awk -F\" '{print $6}'`
#Create the content file
	sed -e 's#<atom:content/>.*$##' ${METADATA} >${METADATA_FILE}

#    echo -n '<content type="application/octet-stream">' >> ${METADATA_FILE}
    echo -n '<atom:content type="'${TYPE}'">' >> ${METADATA_FILE}
	openssl base64 -in files/$FILE -out ${METADATA}.base64
	cat ${METADATA}.base64 | tr -d '\n'  >>${METADATA_FILE}
	echo -n '</atom:content>' >> ${METADATA_FILE}
	#if TYPE = text/*
#cat $files/$FILE | sed -e 's#<?xml version="1.0" encoding="UTF-8"?>##' >> ${METADATA_FILE}	
	sed -e 's#^.*<atom:content/>##' ${METADATA} >>${METADATA_FILE}
	
	if [ $UPDATE = 'true' ]
	then
		UPDATE_URL=`grep edit-media ${METADATA_CREAT} | awk -F\" '{print $4}'`
		curl -uadmin:admin -X PUT -HContent-type:application/atom+xml  --data @${METADATA_FILE} ${UPDATE_URL}
	else
		curl -uadmin:admin -X POST -HContent-type:application/atom+xml -o ${METADATA_CREAT} --data @${METADATA_FILE} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children
	fi
	
	
done