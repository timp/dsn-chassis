# Introduction #

A description of a web service which will validate a Chassis Atom entry, according to an XML schema, and store the entry in a relational database


# Details #

The service uses Spring to create the service itself and HyperJaxb3, a combination of JAXB and JPA.

JAXB is used to marshal, unmarshal and validate the XML that is PUT or POSTed to the service. This is a straightforward and standard usage of JAXB.

HyperJAXB3 is used to further annotate the generated beans with JPA annotations so that the unmarshalled beans can be persisted to a relational database.

If the entry is valid then after it has been stored it's returned with a load of Hjid attributes giving id's for the various tables it's inserted into - if the entry is not valid then some error XML is generated. (note you can't roundtrip the entry because of these attributes which fail the validation)

If you'd like to try it out here is the quick start guide...

From https://dsn-chassis.googlecode.com/svn/trunk/
Get the chassis-model project and mvn install
This generates all the beans you need for JAXB and JPA

Get the chassis-rest project

You need to install org.springframework.instrument.tomcat.jar from Spring's dist/weaver directory into Tomcat's lib directory. (actually spring-instrument-tomcat-3.0.5.RELEASE.jar)
> (maven spring-instrument-tomcat)

The default database is hypersonic in memory - if you want to change it e.g. for MySQL then you can do so by editing src/main/resources/spring/database.properties - there are example values for MySQL in the file

The applicationContext.xml file in the same directory controls various stuff - of particular interest is the property 

&lt;prop key="eclipselink.ddl-generation"&gt;

create-tables

&lt;/prop&gt;


which tells eclipselink to create tables as it needs them.

Now you can mvn install and run the app.

Get yourself an entry and post it to the service like this:
$ curl -X POST -HContent-type:application/xml  --data @ACXFX.xml http://localhost:8080/chassis-rest/service/study

Fetch it again by
curl -HAccept:application/xml http://localhost:8080/chassis-rest/service/study/ACXFX

Where ACXFX is the value of manta:id element in the stored entry