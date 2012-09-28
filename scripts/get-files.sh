. ./config.sh

TARGET=${HOST}/service/content/media/submitted

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource
curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


curl -s -S -k -b ${COOKIES} -o submitted.xml ${TARGET}
curl -s -S -k -b ${COOKIES} -o curated.xml ${HOST}/service/content/media/curated
curl -s -S -k -b ${COOKIES} -o derivations.xml ${HOST}/service/content/derivations

if [[ $(uname) == Darwin ]]
then
 SIZE_COMMAND='stat -f %z'
else
 SIZE_COMMAND='stat -c %s'
fi

mv ${STUDIES_DIR} ${STUDIES_DIR}.$$
mkdir ${STUDIES_DIR}
for j in submitted curated
do
	rm -rf $j

	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j.xml -XSL split-feed.xsl -OUT foo.out
	
	mv ${STUDIES_DIR} $j
done
mv ${STUDIES_DIR}.$$ ${STUDIES_DIR}

#rm -rf files
mkdir files
for i in submitted/* curated/*
do
	URL=`grep edit-media $i | awk -F\" '{print $8}' `
	NAME=`echo -n ${URL} | awk -F/ '{print $NF}'`
	RSIZE=`grep edit-media $i | awk -F\" '{print $10}'`
	if [ test -f files/${NAME} ]
	then
		SIZE=`$SIZE_COMMAND files/${NAME}`
		if [ ${SIZE} -ne ${RSIZE} ]
		then
			rm files/${NAME}
		fi
	fi
	test -f files/${NAME} || curl -s -S -k -b ${COOKIES} -o files/${NAME} ${URL}
done

