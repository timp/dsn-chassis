echo "Stop the server"
read
rm -rf /cygdrive/c/data/atombeat/exist/*
echo "Start the server"
read
rm -rf exist-backup
#This must be the same as on the live server
ATOMBEAT_EXIST_PASS=

unzip -q `ls -rt full*.zip|tail -1` -d exist-backup
export EXIST_HOME=../software/existclient
$EXIST_HOME/bin/backup.sh -r exist-backup/db/atombeat/__contents__.xml -u admin -p ${ATOMBEAT_EXIST_PASS} -ouri=xmldb:exist://localhost:8080/repository/xmlrpc
$EXIST_HOME/bin/backup.sh -r exist-backup/db/system/__contents__.xml -u admin -p ${ATOMBEAT_EXIST_PASS} -ouri=xmldb:exist://localhost:8080/repository/xmlrpc

rm -rf exist-backup

echo "Reset the admin password - passwd admin"
EXIST_HOME=../software/existclient
$EXIST_HOME/bin/client.bat -u admin -P $ATOMBEAT_EXIST_PASS -ouri=xmldb:exist://localhost:8080/repository/xmlrpc -s
echo "Restart the server"
read
curl -uadam@example.org:bar -X POST -d "oldhost=https://www.wwarn.org/repository/service&newhost=http://localhost:8080/repository/service" http://localhost:8080/repository/service/admin/changehost.xql
