




for i in `grep -l 'Internal Error' cmis-files/*.xml.creat`
do
	echo $i
	grep 'WWARN/Studies' $i | awk -F\> '{print $4}'
	egrep '(Exception|Constraint)' $i | grep -v 'Wrapped Exception' | grep -v ScriptException | grep -v ExceptionTranslatorMethodInterceptor | grep -v createStatusException 
done

#grep 'The Web Script' cmis-files/*.xml.creat | grep 404 | awk -F\< '{print $4}' | awk -F\> '{print $2}' | awk -F\/ '{print $8}' | sort -u > deletedStudies
echo "Error in creation"
for i in `grep -l 'Web Script Status 404' cmis-files/*.xml.creat`
do
	echo $i
done

egrep 'studyFileNodeRef=$' set-derivations.out

echo "Files not created"
for i in cmis-files/*.xml
do 
	if [ ! -f $i.creat ]
	then 
		echo $i
	fi
done