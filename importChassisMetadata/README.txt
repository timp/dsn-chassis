Make sure you add org.springframework.instrument.tomcat.jar from Spring's 
dist/weaver directory into Tomcat's lib directory. 

cp ~/.m2/repository/org/springframework/spring-instrument-tomcat/3.0.5.RELEASE/spring-instrument-tomcat-3.0.5.RELEASE.jar /usr/share/tomcat7/lib/ 

The access control for the database is in the pom (which updates context.xml)
A named user, who does not have a password, is manually configured on the database server. 

curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @studies.xml http://localhost:8080/importChassisMetadata/service/studies


