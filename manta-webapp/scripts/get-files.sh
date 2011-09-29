USERNAME=adam@example.org
PASSWORD=bar
HOST=https://cloud1.cggh.org/repository
TICKETS=http://cloud1.cggh.org/sso/v1/tickets

#USERNAME=
#PASSWORD=
#HOST=https://wwarn-app3.nsms.ox.ac.uk/repository
#TICKETS=https://wwarn-app3.nsms.ox.ac.uk/sso/v1/tickets

#USERNAME=
#PASSWORD=
#HOST=https://www.wwarn.org/repository
#TICKETS=https://www.wwarn.org/sso/v1/tickets


TARGET=${HOST}/service/content/media/submitted

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource
curl -s -S -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


curl -s -S -k -b cookie-jar -o submitted.xml ${TARGET}
curl -s -S -k -b cookie-jar -o curated.xml ${HOST}/service/content/media/curated
curl -s -S -k -b cookie-jar -o derivations.xml ${HOST}/service/content/derivations

if [[ $(uname) == Cygwin ]]
then
 SEPARATOR=';'
else
 SEPARATOR=':'
fi
if [[ $(uname) == Darwin ]]
then
 SIZE_COMMAND='stat -f %z'
else
 SIZE_command='stat -c %s'
fi


CLASSPATH=.
for i in `ls jars/*`
do
        CLASSPATH="$CLASSPATH$SEPARATOR./$i"
done
echo classpath ${CLASSPATH}


mv studies studies.$$
mkdir studies
for j in submitted curated
do
	rm -rf $j

	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j.xml -XSL split-feed.xsl -OUT foo.out
	
	mv studies $j
done
mv studies.$$ studies

#rm -rf files
mkdir files
for i in submitted/* curated/*
do
	URL=`grep edit-media $i | awk -F\" '{print $8}' `
	NAME=`echo -n ${URL} | awk -F/ '{print $NF}'`
	RSIZE=`grep edit-media $i | awk -F\" '{print $10}'`
	if [ -f files/${NAME} ]
	then
		SIZE=`$SIZE_COMMAND files/${NAME}`
		if [ ${SIZE} -ne ${RSIZE} ]
		then
			rm files/${NAME}
		fi
	fi
	test -f files/${NAME} || curl -s -S -k -b cookie-jar -o files/${NAME} ${URL}
done

