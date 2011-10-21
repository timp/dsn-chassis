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

diff ${TEMP_NAMES} ${TEMP_UNIQUE_NAMES} | sort -u | grep \< | awk '{print $2}' > ${TEMP_DUPLICATE_NAMES}


mkdir duplicates
for i in `cat ${TEMP_DUPLICATE_NAMES}`
do
	DATE_UPLOADED=0
	LAST_FILE=
	for j in `grep "$i" ${TEMP_FILE}`
	do
		FILE=`echo -n $j | awk -F# '{print $1}'`
		DATE=`java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN ${FILE} -XSL filedate.xsl | sed -e 's/Z$//' -e 's/+01:00$//'`
		DATE_STAMP=`date -d $DATE +%s`
		if [ $DATE_STAMP -gt ${DATE_UPLOADED} ]
		then
			if [ ${DATE_UPLOADED} -gt 0 ]
			then
				mv $LAST_FILE duplicates
			fi
			DATE_UPLOADED=$DATE_STAMP
			LAST_FILE=${FILE}
		else
			mv $FILE $duplicates
		fi
	done
done