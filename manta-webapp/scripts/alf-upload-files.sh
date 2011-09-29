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

UPDATE=false
DIR=cmis-files
mkdir ${DIR}
for j in submitted/* curated/*
do
	NAME=`echo -n $j| sed -e 's#submitted/##' | sed -e 's#curated/##'`
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
	
	# delete all line ends
	cat ${METADATA}.base64 | tr -d '\n'  >>${METADATA_FILE}
	echo -n '</atom:content>' >> ${METADATA_FILE}
	#if TYPE = text/*
#cat $files/$FILE | sed -e 's#<?xml version="1.0" encoding="UTF-8"?>##' >> ${METADATA_FILE}	
	sed -e 's#^.*<atom:content/>##' ${METADATA} >>${METADATA_FILE}
	echo "Alf file url"  ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children/${METADATA_CREAT}
	
	if [ $UPDATE = 'true' ]
	then
	  #FIXME need check that content is not the alfresco error page
		UPDATE_URL=`grep edit-media ${METADATA_CREAT} | awk -F\" '{print $4}'`
		curl -s -uadmin:admin -X PUT -HContent-type:application/atom+xml  --data @${METADATA_FILE} ${UPDATE_URL}
	else
		curl -s -uadmin:admin -X POST -HContent-type:application/atom+xml -o ${METADATA_CREAT} --data @${METADATA_FILE} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/children
	fi
	
	
done