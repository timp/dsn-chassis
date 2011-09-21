USERNAME=adam@example.org
PASSWORD=bar
HOST=http://cloud1.cggh.org/repository
TICKETS=http://cloud1.cggh.org/sso/v1/tickets
TARGET_STUDY=CGBFH

#USERNAME=
#PASSWORD=
#HOST=https://wwarn-app3.nsms.ox.ac.uk/repository
#TICKETS=https://wwarn-app3.nsms.ox.ac.uk/sso/v1/tickets
#TARGET_STUDY=CZEHU

#USERNAME=
#PASSWORD=
#HOST=https://www.wwarn.org/repository
#TICKETS=https://www.wwarn.org/sso/v1/tickets
#TARGET_STUDY=XXNBR

TARGET=${HOST}/service/content/studies

TGT=`wget --no-check-certificate --post-data="username=${USERNAME}&password=${PASSWORD}" -q -O - -d ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`wget --no-check-certificate --post-data="service=${TARGET}" -q -O - -d ${TICKETS}/${TGT}`
#Get the resource
curl -k -c cookie-jar -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

for i in uploaddata/*
do
#Make sure that you actually have permission to upload submitted files
curl -k -b cookie-jar -o post.out -i -H "Content-Type: multipart/form-data" -F "media=@$i" -F 'category=scheme="http://www.cggh.org/2010/chassis/scheme/FileTypes"; term="http://www.cggh.org/2010/chassis/terms/DataFile"; label="";' ${HOST}/service/content/media/submitted/${TARGET_STUDY}
done
