USERNAME=
PASSWORD=
HOST=https://cloud1.cggh.org/repository
TICKETS=http://cloud1.cggh.org/sso/v1/tickets

#USERNAME=
#PASSWORD=
#HOST=https://wwarn-app3.nsms.ox.ac.uk/repository
#TICKETS=https://wwarn-app3.nsms.ox.ac.uk/sso/v1/tickets

#USERNAME=
#PASSWORD=
#HOST=https://www.wwarn.org/repository
#TICKETS=https://www.wwarn.org/sso/v1/tickets

ALF_MACHINE_ADDRESS=alfresco:8080
ALF_HOME=http://${ALF_MACHINE_ADDRESS}/alfresco/service
ALF_USERNAME=
ALF_PASSWORD=

COOKIES=cookie-jar
STUDIES=studies.xml
#This is hard coded in split-feed.xsl
STUDIES_DIR=studies

if [[ $(uname) == 'CYGWIN_NT-6.1-WOW64' ]]
then
 SEPARATOR=';'
else
 SEPARATOR=':'
fi

CLASSPATH=.
for i in `ls jars/*`
do
        CLASSPATH="$CLASSPATH$SEPARATOR./$i"
done
echo classpath ${CLASSPATH}
