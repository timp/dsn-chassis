#get-studies.sh
CLASSPATH=.
for i in `ls jars/*`
do
	CLASSPATH="$CLASSPATH;./$i"
done
java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN studies.xml -XSL split-feed.xsl -OUT foo.out
