. ./config.sh

java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN ${STUDIES} -XSL split-feed.xsl -OUT foo.out
