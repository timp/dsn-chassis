#alf-set-derivations.sh

USERNAME=
PASSWORD=
HOST=https://cloud1.cggh.org/repository
TICKETS=https://www.cggh.org/sso/v1/tickets

ALF_USERNAME=
ALF_PASSWORD=
ALF_MACHINE_ADDRESS=www.cggh.org

#USERNAME=
#PASSWORD=
#HOST=https://www.wwarn.org/repository
#TICKETS=https://www.wwarn.org/sso/v1/tickets
#ALF_MACHINE_ADDRESS=www.wwarn.org

TARGET=${HOST}/service/content/derivations


#ALF_MACHINE_ADDRESS=129.67.45.244:8080
ALF_HOME=https://${ALF_MACHINE_ADDRESS}/alfresco/service



if [[ $(uname) == 'CYGWIN_NT-6.1-WOW64' ]]
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

COOKIES=cookie-jar

#You should already have the derivations.xml file from get-studies.sh
if [ true = false ]
then
	TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
	#get Service Ticket
	ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
	#Get the resource
	curl -s -S -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

	curl -s -S -k -b cookie-jar -o derivations.xml ${HOST}/service/content/derivations
fi

DIR=derivations
mkdir ${DIR}
for j in studies/*
do
	NAME=`echo -n $j| sed -e 's#studies/##'`
	STUDY=`echo -n ${NAME}| sed -e 's#.xml##'`
	ALF_OUT=${DIR}/${STUDY}.xml
	SRC=${TARGET}/${STUDY}
	curl -s -S -k -u${ALF_USERNAME}:${ALF_PASSWORD} -o ${ALF_OUT} ${ALF_HOME}/cmis/p/WWARN/Studies/${STUDY}/descendants
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN ${ALF_OUT} -XSL file-association.xsl -OUT urls.$$
	for i in `cat urls.$$`
	do
		curl -s -S -k -u${ALF_USERNAME}:${ALF_PASSWORD}  ${ALF_HOME}${i}
	done
	rm urls.$$
done