This webapp provides a service to enable posts of atom xml and updates a named database: 
chassisDb. 

The database structure is meant to reflect exactly what is held in Chassis, to the extent that 
it should be possible to round trip from chassisDb back to chassis.

Should nodes spontaneously corrupt within eXists, as they are known to, then the tests on this 
project should detect that.  

The access control for the database is in src/main/resources/META-INF/database.properties
A named user, who does not have a password, is manually configured on the database server. 

curl -X POST -HContent-type:application/xml  -HAccept:application/xml --data @/var/chassis-download/www.wwarn.org/studies/TETAJ.xml http://localhost:8080/chassis-pruned/service/study


To handle passwords you will need to add a file /var/chassis-isomorphic/chassisDb.properties 
containign a line password=<password> then 

mysql> create user chassisDb@localhost identified by '<password>';
mysql> grant all on chassisDb.* to chassisDb;
mysql> grant all on chassispruned.* to chassisDb;

