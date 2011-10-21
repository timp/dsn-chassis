for i in `grep -l 'Internal Error' cmis-files/*.xml.creat`
do
	echo $i
	grep 'WWARN/Studies' $i | awk -F\> '{print $4}'
	egrep '(Exception|Constraint)' $i | grep -v 'Wrapped Exception' | grep -v ScriptException | grep -v ExceptionTranslatorMethodInterceptor | grep -v createStatusException 
done

egrep 'studyFileNodeRef=$' set-derivations.out
