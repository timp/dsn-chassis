if [[ $(uname) == Cygwin ]] 
then
 SEPARATOR=';'
else
 SEPARATOR=':'
fi

#get-studies.sh
CLASSPATH=.
for i in `ls jars/*`
do
	CLASSPATH="$CLASSPATH$SEPARATOR./$i"
done
echo classpath ${CLASSPATH}
java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN studies.xml -XSL split-feed.xsl -OUT foo.out
