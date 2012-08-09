
. ./config.sh

TARGET=${HOST}/service/content/studies

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource ticket
curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


#get the resource
DIR=history-files
rm -rf ${DIR}
mkdir ${DIR}
for i in TYKSC AJXCU ESUHR TZXHH GZBJN
do
	for k in 0 10 20 30
	do
		for j in 0 1 2 3 4 5 6 7 8 9 
		do 
			NUM=`expr $k + $j`
			curl -s -S -k -b ${COOKIES} -o ${DIR}/${i}-${NUM}.xml ${HOST}/service/history/studies/${i}?revision=${NUM}
		done
	done
done

