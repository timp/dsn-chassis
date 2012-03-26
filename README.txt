This webapp provides a service to enable posts of atom xml and updates a named database: 
chassisDb. 

The database structure is meant to reflect exactly what is held in Chassis, to the extent that 
it should be possible to round trip from chassisDb back to chassis.

Should nodes spontaneously corrupt within eXists, as they are known to, then the tests on this 
project should detect that.  

The access control for the database is in the pom (which updates context.xml)
A named user, who does not have a password, is manually configured on the database server. 

curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @studies.xml http://localhost:8080/importChassisMetadata/service/studies


