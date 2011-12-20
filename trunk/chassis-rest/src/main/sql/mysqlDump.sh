#!/bin/bash 
if [ ! $1 ]; then 
  echo "Specify a database name (Followed by a password)" 
else
  export out=`date "+%Y%m%d"`
  export file=$1_$out.sql
  mysqldump $1  --user=timp --password=$2 --complete-insert --skip-opt --add-drop-table  > $file
  echo $file
fi


