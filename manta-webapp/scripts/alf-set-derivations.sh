#alf-set-derivations.sh

. ./config.sh

TARGET=${HOST}/service/content/derivations

#You should already have the derivations.xml file from get-studies.sh
if [ ! -f derivations.xml ]
then
	TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
	#get Service Ticket
	ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
	#Get the resource
	curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

	curl -s -S -k -b ${COOKIES} -o derivations.xml ${HOST}/service/content/derivations
fi

DIR=derivations
mkdir ${DIR}
OUT=set-derivations.out
rm ${OUT}
for j in ${STUDIES_DIR}/*
do
	NAME=`echo -n $j| sed -e "s#${STUDIES_DIR}/##"`
	STUDY=`echo -n ${NAME}| sed -e 's#.xml##'`
	ALF_OUT=${DIR}/${STUDY}.xml
	SRC=${TARGET}/${STUDY}
	curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD} -o ${ALF_OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/descendants
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN ${ALF_OUT} -XSL file-association.xsl -OUT urls.$$
	for i in `cat urls.$$`
	do
		echo $i >> ${OUT}
		curl ${CURL_OPTS} -u${ALF_USERNAME}:${ALF_PASSWORD}  ${ALF_HOME}${i} >> ${OUT}
	done
	rm urls.$$
done