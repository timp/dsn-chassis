General

The scripts assume a user of adam@example.org on cloud1, 
to use a different setup you'll need to fill in your account and server details 
at the top of the script before running 

The scripts assume admin:admin for alfresco (FIXME)

To clean the alfresco repository http://alfresco/share/page/repository#filter=path%7C/wwarn/studies 
select all and delete.

Run in this order ( or use run.sh):

get-studies.sh - downloads the studies feed as studies.xml

split-studies.sh - splits into one file per study in the studies directory

The entry directory drives the rest of the scripts as it uses the names of the 
XML files to get the study id for the studies to migrate i.e. make sure that 
there aren't many files in studies during testing.


alf-upload-studies.sh - uploads the studies to alfresco 

Currently the update flag does not appear to work.
Keep UPDATE=false, always delete existing content from alfresco


get-files.sh - downloads the files from chassis 

alf-upload-files.sh - upload the files to alfresco 
Need to check that all the metadata is correctly populated (in file-cmis.xsl) 
especially submitter.


TODO
The derived-from associations need to be set up 
(comments should already be done) - the syntax for the REST calls for this is:

(GET) ${ALF_HOME}/wwarn/createDerivedAssoc?outputFileNodeRef=" + outputFileNodeRef + "&studyFileNodeRef=" + studyFileNodeRef;

Because this needs the Alfresco node ref probably the right way to do this is 
to upload the files then download the feed 

http://alfresco:8080/alfresco/service/cmis/p/WWARN/Studies/MTSUX/children

(which contains the Alfresco node 
ref and Chassis node refs) and cross-reference with the derivations feed 
- this could probably be done by using a much simplified version of 
file-cmis.xsl to produce a script file and running that.
