ChassisDb Data Sources
======================

ChassisDb is a relational view of the data held within the XML tree of Chassis.

The database is created periodically as a snapshot of the contents of Chassis 
at a particular date.

The database is made available in MySQL dump format and a number of CSV files. 

The CSV files are intended to be a 'best slice' through the tree. Chassis is 
able to store the tree shaped model of the study meta-data, flat CSV files 
will inevitably lead to a duplicated view, say if there is more than one site 
for a study. 

The queries are run against all studies and against a subset of the studies for 
each module. 

The following fields are returned.

Studies: 
   StudyId, 
   DateReceived, 
   Modules, 
   StartDate, 
   EndDate, 
   StudyTitle

StudySites:    
   StudyId, 
   DateReceived,
   Modules, 
   StartDate, 
   EndDate, 
   Site,
   Population,
   Latitude,
   Longitude,
   StudyTitle
 
StudySitePublications:    
   StudyId, 
   DateReceived, 
   Modules, 
   StartDate, 
   EndDate, 
   Site,
   Population,
   Latitude,
   Longitude,
   StudyTitle,
   PublicationTitle
   
   
 




