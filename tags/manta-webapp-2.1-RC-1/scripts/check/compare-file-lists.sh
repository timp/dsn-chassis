. ./config.sh

TARGET=${HOST}/service/content/media/submitted

TGT=`curl -s -S -k -D - -d "username=${USERNAME}&password=${PASSWORD}" ${TICKETS} 2>&1 | grep Location | grep TGT | awk -F/ '{printf $NF}'`
#get Service Ticket
ST=`curl -s -S -k -d "service=${TARGET}" -q ${TICKETS}/${TGT}`
#Get the resource
curl -s -S -k -c ${COOKIES} -o studies.ticket -i -H "Accept: text/xml" -X GET ${TARGET}?ticket=$ST

LIST=chassis-files.txt

curl -s -S -k -b ${COOKIES} -o submitted.xml ${TARGET}
curl -s -S -k -b ${COOKIES} -o curated.xml ${HOST}/service/content/media/curated

rm ${LIST}
for j in submitted curated
do
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $j.xml -XSL parse-file-list.xsl >> ${LIST} 
done


curl -k -X POST -u${ALF_USERNAME}:${ALF_PASSWORD} -o alf-files.xml "${ALF_HOME}/cmis/queries" -H "Content-Type:application/cmisquery+xml" -d @cmis-list-study-files.xml
curl -k -X POST -u${ALF_USERNAME}:${ALF_PASSWORD} -o alf-explorer-files.xml "${ALF_HOME}/cmis/queries" -H "Content-Type:application/cmisquery+xml" -d @cmis-list-explorer-data.xml


java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN alf-files.xml -XSL parse-alf-file-list.xsl > alfresco-files.txt
java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN alf-explorer-files.xml -XSL parse-alf-file-list.xsl >> alfresco-files.txt
sort alfresco-files.txt > alf-sorted.txt
sort ${LIST} > chassis-sorted.txt

perl remove-old-versions.pl  chassis-sorted.txt | sort > chassis-sorted-dates.txt

#Remove date stamp because they will be different
#Prefix with study id to make comparison easier
#Remove submitted/curated as there is no real difference
awk -F# '{print $1"#"$2}' alf-sorted.txt | awk -F/ '{printf "%s#",$9;print;}' | sed -e 's/curated//' -e 's/submitted//' | sort > alf-sorted-keys.txt
awk -F# '{print $1"#"$2}' chassis-sorted-dates.txt | awk -F/ '{printf "%s#",$9;print;}' | sed -e 's/curated//' -e 's/submitted//' | sort > chassis-sorted-keys.txt

./get-studies.sh
grep atom:title studies.xml | awk -F\> '{print $2}' | awk -F\< '{print $1}' | sort -u > current-studies.txt
awk -F# '{print $1}' chassis-sorted-keys.txt | sort -u > all-studies.txt
#Remove files from deleted studies 
for i in `diff current-studies.txt all-studies.txt | grep '>' | awk '{print $2}'`
do
    egrep -v "^$i" chassis-sorted-keys.txt > tmp.$$
    mv tmp.$$ chassis-sorted-keys.txt
done

diff alf-sorted-keys.txt chassis-sorted-keys.txt