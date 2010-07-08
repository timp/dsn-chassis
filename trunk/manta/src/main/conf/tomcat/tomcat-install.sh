# See http://www.howtoforge.com/apache2_tomcat5_mod_jk_integration

# setup manta source tree
cd /usr/local/src
ln -s /var/lib/hudson/jobs/manta/workspace/manta .


# install the Apache Portable Runtime
apt-get install  libapr1 libaprutil1

#install tomcat6
apt-get install tomcat6
apt-get install tomcat6-docs
cd /etc/tomcat6/
rm server.xml
ln -s /<dist>/manta/src/main/conf/tomcat/server.xml .

# Edit  tomcat-users.xml to add 
#
#  <role rolename="manager" />
#
#  <!-- Probe roles -->
#  
#  <role rolename="poweruser" />
#  <role rolename="poweruserplus" />
#  <role rolename="probeuser" />
#
#  <user username="admin" password="" roles="manager,admin" />
#

# Install probe from 
# http://www.lambdaprobe.org/d/installation.shtml


