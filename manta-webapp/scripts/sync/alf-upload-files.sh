#alf-upload-files.sh

. ./config.sh

UPDATE=false
DIR=cmis-files
ARCHIVE=archive
mkdir ${DIR}
rm -rf ${ARCHIVE}
mkdir ${ARCHIVE}
#
for j in ${COPY_DIR}/* 
do
	NAME=`echo -n $j| sed -e s"#${COPY_DIR}/##"`
	STUDY=`grep originStudy $j | awk -F\" '{print $10}'`
	grep ${STUDY} deletedStudies
	if [ $? -eq 0 ]
	then
		mv $j ${ARCHIVE}
		continue
	fi	
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
	
	# delete all line ends
	cat ${METADATA}.base64 | tr -d '\n'  >>${METADATA_FILE}
	echo -n '</atom:content>' >> ${METADATA_FILE}
	#if TYPE = text/*
#cat $files/$FILE | sed -e 's#<?xml version="1.0" encoding="UTF-8"?>##' >> ${METADATA_FILE}	
	sed -e 's#^.*<atom:content/>##' ${METADATA} >>${METADATA_FILE}
	echo "Alf file url"  ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children/${METADATA_CREAT}
	
	ORIG=`grep "${NAME}" dupdef | awk -F# '{print $2}'`
	if [ "${ORIG}" = '' -o "${ORIG}" = "${NAME}" ]
	then
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X POST -HContent-type:application/atom+xml -o ${METADATA_CREAT} --data @${METADATA_FILE} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children
	else	
		UPDATE_URL=`grep edit-media cmis-files/${ORIG}.creat | awk -F\" '{print $4}'`
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -X PUT -HContent-type:application/atom+xml -o ${METADATA_CREAT} --data @${METADATA_FILE} ${UPDATE_URL}
	fi

	
done