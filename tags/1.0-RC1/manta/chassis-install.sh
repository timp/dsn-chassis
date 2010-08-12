# this is a putative script for deploying a new release of chassis manta to the wwarn alpha
# server platform - AS YET UNTESTED! - these instructions are executed on app1.alpha.wwarn.org
# N.B. script will need to be run as root

# TODO this is a bit out of date and missing some crucial steps

TAG=chassis-manta-1.0-beta-SNAPSHOT-20100617
URL=https://alpha.wwarn.org/$TAG
DEPLOY=/opt/$TAG
DATA=/data/$TAG
SECCONF=/opt/chassis-manta-1.0-alpha-SNAPSHOT-20100419/WEB-INF/security-cas-drupal.properties

# step 1 - obtain war to deploy
wget http://dsn-chassis.googlecode.com/files/$TAG.war

# step 2- unpack war
unzip $TAG.war -d $DEPLOY
chown -R tomcat6:tomcat6 $DEPLOY

# step 3 - configure security for cas/drupal
sed -i -e 's|security-example.xml|security-cas-drupal.xml|' $DEPLOY/WEB-INF/web.xml
cp $SECCONF $DEPLOY/WEB-INF/

# step 4 - configure atombeat service url
sed -i -e 's|http://cloud1.cggh.org/manta|$URL|' $DEPLOY/atombeat/config/shared.xqm

# step 5 - configure exist data location
mkdir $DATA
chown -R tomcat6:tomcat6 $DATA
sed -i -e 's|exist-data|$DATA|' $DEPLOY/WEB-INF/exist-conf.xml

# step 6 - link to tomcat webapps directory
ln -s $DEPLOY /var/lib/tomcat6/webapps

# step 7 - restart tomcat
/etc/init.d/tomcat6 restart

# that's all - don't forget to configure a reverse proxy url on the alpha.wwarn.org web server!

# also, you might want to check the version tag displayed on the footer of each page on manta and edit accordingly...
# cd /opt/chassis-manta-1.0-beta-SNAPSHOT-20100617/WEB-INF/resources/config/
# sudo emacs theme-manta.xsl 

