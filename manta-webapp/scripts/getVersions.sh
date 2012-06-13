#!/bin/bash

# Usage: ./getVersions.sh <chassiId>

# 2012-06-12 From IW  
# 2012-06-13 Repurposed by TPP

STUDY=$1

USERNAME=
PASSWORD=
HOST=https://www.wwarn.org/repository
TICKETS=https://www.wwarn.org/sso/v1/tickets
COOKIES=cookie-jar

TARGET=${HOST}/service/content/studies

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource ticket
curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

#get the resource
DIR=versions
rm -rf ${DIR}
mkdir ${DIR}
for i in $STUDY
do
  # revisions are numbered from 1
  for (( j=1; ; j++ )) 
  do
    curl -s -S -k -b ${COOKIES} -o ${DIR}/${i}-${j}.xml ${HOST}/service/history/studies/${i}?revision=${j}
    grep -q '<status>404</status>' ${DIR}/${i}-${j}.xml
    if [ $? == 0 ]
    then 
      echo "Overshot ${DIR}/${i}-${j}.xml to be deleted" 
      rm ${DIR}/${i}-${j}.xml
      break
    fi
    if [ "$j" -gt "1" ]
    then 
     PREV=`expr $j - 1`
     echo
     echo diff ${DIR}/${i}-${PREV}.xml  ${DIR}/${i}-${j}.xml
     diff ${DIR}/${i}-${PREV}.xml  ${DIR}/${i}-${j}.xml
   fi
  done
done

