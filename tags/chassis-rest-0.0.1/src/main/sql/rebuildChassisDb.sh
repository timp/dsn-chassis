#!/bin/bash                                                                                           

# Call this from cron on mysql machine       
# NOTE that the current unix user is expected to have authorisation in ~/.my.cnf
set -o errexit

export out=`date "+%Y%m%d"`

export file=chassisDb_$out.sql


mysql chassisDb < $file

touch ./cronLastRunAt_$out





