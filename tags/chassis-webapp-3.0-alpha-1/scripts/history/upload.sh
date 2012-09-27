
DIR=history-files
NEW=uploaded-files
rm -rf ${NEW}
mkdir ${NEW}
for i in TYKSC AJXCU ESUHR TZXHH GZBJN
do
	NEW_FILE=${NEW}/${i}.xml
	curl -s -S -ucora@example.org:bar -H Content-Type:application/atom+xml -X POST --data-binary @${DIR}/${i}-1.xml http://localhost:8080/repository/service/content/studies > ${NEW_FILE}
	NEW_ID=`grep manta:id ${NEW_FILE} | awk -F\> '{print $2}' | awk -F\< '{print $1}'`
	echo $NEW_ID
#	for j in 2 3 4 5 6 7 8 9 10 11 12 13 14
	for j in 99
	do
		sed -e "s/$i/${NEW_ID}/g;" -e 's#https://www.wwarn.org#http://localhost:8080#g' ${DIR}/${i}-${j}.xml > ${NEW}/${NEW_ID}-$j.xml
		grep priorAntimalarialsExclusion ${NEW}/${NEW_ID}-${j}.xml
		if [ $? = 0 ]
		then
			curl -s -S -ucora@example.org:bar -H Content-Type:application/atom+xml -X PUT --data-binary @${NEW}/${NEW_ID}-${j}.xml http://localhost:8080/repository/service/content/studies/${NEW_ID} > /dev/null
		else
			curl -s -S -ucora@example.org:bar http://localhost:8080/repository/service/content/studies/${NEW_ID} > ${NEW}/${NEW_ID}.xml
			grep priorAntimalarialsExclusion ${NEW}/${NEW_ID}.xml
			if [ $? = 1 ]
			then 
				echo "Missing data for ${NEW_ID}"
			fi
		fi
	done
done
