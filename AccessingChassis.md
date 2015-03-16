# Introduction #

The Chassis REST interface can be deployed behind either CAS or Basic Auth security together with Spring Security

## CAS Authentication ##
Key points are that the CAS RESTlet interface must be enabled to allow initial authentication and subsequently the Spring Security JSESSIONID cookie must be used.

## REST Interface ##
A detailed introduction to using the REST interface can be found as part of the AtomBeat wiki http://code.google.com/p/atombeat/wiki/TutorialGettingStarted

# A script to authenticate against CAS #

There are a collection of scripts in `manta-webapp/scripts`, similar to the example below:

```
USERNAME=
PASSWORD=
HOST=http://hostname/repository
TICKETS=http://hostname/sso/v1/tickets
TARGET=${HOST}/service/content/studies
DEST=studies.xml

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`

#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`

#Get the resource ticket
curl -s -S -k -c cookie-jar.txt -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

#Get the resource
curl -s -S -k -b cookie-jar.txt -o ${DEST} ${TARGET}

#Replace the resource
#curl -b cookie-jar -o post.out -i -H "Content-Type: application/atom+xml" --data-binary @new-study ${HOST}/service/content/studies
```