
. ./config.sh

TARGET=${HOST}/service/content/studies

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource ticket
curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


#get the resource
curl -s -S -k -b ${COOKIES} -o ${STUDIES} ${HOST}/service/content/studies

