See:
  http://code.google.com/p/dsn-chassis/wiki/ChassisRESTNotes
These notes are based up and reflect 
  http://code.google.com/p/dsn-chassis/wiki/ChassisRESTRDBMS
  

I've wanted to try and use a more schema based approach for the study 
(and study-info) in Chassis so I've been playing with JAXB and Spring 
to create a REST web service which will validate a Chassis study 
against an XML schema.

The schema itself is auto-generated at the moment so it will accept anything 
that is currently in Chassis - over time this should become more strict.

If the entry is valid it's returned with a load of Hjid attributes giving id's 
in various tables inserted - if not valid then some error XML is generated. 
(note you can't roundtrip the entry because of these attributes which fail the validation)

From https://dsn-chassis.googlecode.com/svn/trunk/
Get the chassis-model project and mvn install

This generates all the beans you need for JAXB and JPA

Get the chassis-rest project

This is a Spring app. 
You will need to add the Spring class loader to tomcat: 

As root: 
cp  /home/timp/.m2/repository/org/springframework/spring-instrument-tomcat/3.0.5.RELEASE/spring-instrument-tomcat-3.0.5.RELEASE.jar /usr/share/tomcat6/lib/

/etc/init.d/tomcat6 restart


The default database is hypersonic in memory - 
if you want to change it e.g. for MySQL then you can do so by editing 
src/main/resources/spring/database.properties 

The applicationContext.xml file in the same directory controls various stuff:
of particular interest is the property <prop key="eclipselink.ddl-generation">create-tables</prop>
which tells eclipselink to create tables as it needs them.

FIXME: Make a directory called /home/timp/workspace/chassis-rest/db


Now you can mvn install and run the app.
mvn -X clean install tomcat:redeploy

Get yourself an entry and post it to the service like this:
$ curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @TBYKQ.xml http://localhost:8080/chassis-rest/service/study
Fetch it again by
curl -HAccept:application/xml http://localhost:8080/chassis-rest/service/study/TBYKQ

If you're using MySQL this will fail - you need to stop the app - 
and run the script chassis-model/src/main/resources/fix-db.sql 
Then restart (The short answer as to why this is necessary is because of 
having element names longer than 31 characters...)
After you've done that it should work fine 
(although I do expect other database mapping errors to crop up to start with...)

 

