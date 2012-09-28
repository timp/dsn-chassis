. ./config.sh

#If there are two files of the same name move the later one to the duplicates directory
#N.B. Won't work if there are more than two
TEMP_FILE=filenames.$$
TEMP_NAMES=${TEMP_FILE}.names
TEMP_UNIQUE_NAMES=${TEMP_NAMES}.unique
TEMP_DUPLICATE_NAMES=${TEMP_NAMES}.dups

for i in submitted/* curated/*
do
	FILE=`java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN ${i} -XSL filename.xsl`
	echo "$i#${FILE}" >> ${TEMP_FILE}
done
awk -F# '{print $2}' ${TEMP_FILE} | sort > ${TEMP_NAMES}
sort -u ${TEMP_NAMES} > ${TEMP_UNIQUE_NAMES}

diff ${TEMP_NAMES} ${TEMP_UNIQUE_NAMES} | sort -u | grep \< | sed -e 's/< //' > ${TEMP_DUPLICATE_NAMES}


DUPLICATE_DEF=dupdef
rm ${DUPLICATE_DEF}
mkdir duplicates

while read i
do
	DATE_UPLOADED=0
	LAST_FILE=
	grep "$i" ${TEMP_FILE} > ${TEMP_FILE}.2
	while read j
	do
		FILE=`echo -n $j | awk -F# '{print $1}'`
		DATE=`java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN "${FILE}" -XSL filedate.xsl | sed -e 's/Z$//' -e 's/+01:00$//'`
		DATE_STAMP=`date -d $DATE +%s`
		if [ $DATE_STAMP -gt ${DATE_UPLOADED} ]
		then
			if [ ${DATE_UPLOADED} -gt 0 ]
			then
				mv $LAST_FILE duplicates
				echo "$LAST_FILE#$FILE" | sed -e 's#submitted/##g' | sed -e 's#curated/##g'  >> ${DUPLICATE_DEF}
			fi
			DATE_UPLOADED=$DATE_STAMP
			LAST_FILE=${FILE}
		else
			mv $FILE duplicates
			echo "$FILE#$LAST_FILE" | sed -e 's#submitted/##g' | sed -e 's#curated/##g' >> ${DUPLICATE_DEF}
		fi
	done < ${TEMP_FILE}.2
done < ${TEMP_DUPLICATE_NAMES}