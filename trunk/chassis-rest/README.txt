See:
  http://code.google.com/p/dsn-chassis/wiki/ChassisRESTNotes
These notes are based up and reflect 
  http://code.google.com/p/dsn-chassis/wiki/ChassisRESTRDBMS
  
Introduction
============

If the entry is valid it is returned with a load of Hjid attributes giving id's 
in various tables inserted - if not valid then some error XML is generated. 
(note you can't roundtrip the entry because of these attributes which fail the validation)

Setup
======

From https://dsn-chassis.googlecode.com/svn/trunk/
Get the chassis-download project and mvn install
This downloads the studies feed and links feed to your local disk. 

Get the chassis-model project and mvn install
This generates all the beans you need for JAXB and JPA

Get the chassis-rest project

This is a Spring app. 
You will need to add the Spring class loader to tomcat: 

As root (locally): 
cp  /home/timp/.m2/repository/org/springframework/spring-instrument-tomcat/3.0.5.RELEASE/spring-instrument-tomcat-3.0.5.RELEASE.jar /usr/share/tomcat6/lib/
(or on charlie) 
cp /home/timp/spring-instrument-tomcat-3.0.5.RELEASE.jar /opt/apache-tomcat-latest/lib/


/etc/init.d/tomcat6 restart
(Note Tomcat will need to have its defaults modified 
(in /etc/defaults/tomcat6 in ubuntu) to something like:
JAVA_OPTS="-Djava.awt.headless=true -Xms256M -Xmx768M -XX:MaxPermSize=256m -XX:+
CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled"


Deploy to your webserver then run tests. 
The database definitions are in src/main/resources/spring/database.properties 

The applicationContext.xml file in the same directory controls table creation: 
<prop key="eclipselink.ddl-generation">create-tables</prop>
which tells eclipselink to create tables as it needs them.

Now you can mvn install and run the app.
mvn clean install tomcat:redeploy -Dmaven.test.skip
mvn test 

Get yourself an entry and post it to the service like this:
$ curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @TBYKQ.xml http://localhost:8080/chassis-rest/service/study
Fetch it again by
curl -HAccept:application/xml http://localhost:8080/chassis-rest/service/study/TBYKQ

Remember, Spring caches, so you will need to restart tomcat sometimes. 


 

