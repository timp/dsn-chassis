# See http://www.howtoforge.com/apache2_tomcat5_mod_jk_integration

# setup manta source tree
cd /usr/local/src
ln -s /var/lib/hudson/jobs/manta/workspace/manta .


# install the Apache Portable Runtime
apt-get install  libapr1 libaprutil1

#install tomcat6
apt-get install tomcat6
apt-get install tomcat6-docs
apt-get install tomcat6-admin
cd /etc/tomcat6/
rm server.xml
ln -s /<dist>/manta/src/main/conf/tomcat/server.xml .
cd /usr/share/tomcat6/bin
ln -s /<dist>/manta/src/main/conf/tomcat/setenv.sh .

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

wget http://www.lambdaprobe.org/downloads/1.7/probe.1.7b.zip
unzip probe.1.7b.zip
cp probe.war /var/lib/tomcat6/webapps/

# Build Manta
mvn clean install 
cp target/manta.war /var/lib/tomcat6/webapps/

/etc/init.d/tomcat6 restart


