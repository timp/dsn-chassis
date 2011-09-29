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


TARGET=${HOST}/service/content/studies

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource ticket
curl -s -S -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST


#get the resource
curl -s -S -k -b cookie-jar -o studies.xml ${HOST}/service/content/studies

