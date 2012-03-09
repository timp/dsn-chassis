!#/bin/bash 

mvn -Dmaven.test.skip=true clean install tomcat:redeploy

mvn -Dmaven.test.failure.ignore=false -Djetty.skip=true test