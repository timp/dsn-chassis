USERNAME=
PASSWORD=
HOST=http://cloud1.cggh.org/repository
TICKETS=http://cloud1.cggh.org/sso/v1/tickets

USERNAME=
PASSWORD=
HOST=https://wwarn-app3.nsms.ox.ac.uk/repository
TICKETS=https://wwarn-app3.nsms.ox.ac.uk/sso/v1/tickets

USERNAME=
PASSWORD=
HOST=https://www.wwarn.org/repository
TICKETS=https://www.wwarn.org/sso/v1/tickets


TARGET=${HOST}/service/content/media/submitted

TGT=`curl -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource
curl -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


curl -k -b cookie-jar -o submitted.xml ${TARGET}
curl -k -b cookie-jar -o curated.xml ${HOST}/service/content/media/curated

CLASSPATH=.
for i in `ls jars/*`
do
	CLASSPATH="$CLASSPATH;./$i"
done

mv entries entries.$$
mkdir entries
for j in submitted curated
do
	rm -rf $j

	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j.xml -XSL split-feed.xsl -OUT foo.out
	
	mv entries $j
done
mv entries.$$ entries

#rm -rf files
mkdir files
for i in submitted/* curated/*
do
	URL=`grep edit-media $i | awk -F\" '{print $8}' `
	NAME=`echo -n ${URL} | awk -F/ '{print $NF}'`
	RSIZE=`grep edit-media $i | awk -F\" '{print $10}'`
	if [ -f files/${NAME} ]
	then
		SIZE=`stat -c %s files/${NAME}`
		if [ ${SIZE} -ne ${RSIZE} ]
		then
			rm files/${NAME}
		fi
	fi
	test -f files/${NAME} || curl -k -b cookie-jar -o files/${NAME} ${URL}
done