General
You'll need to fill in your account and server details at the top of the script before running 
- the scripts assume admin:admin for alfresco (bad...)

Run in roughly this order:

get-studies.sh - downloads the studies feed as studies.xml

split-studies.sh - splits into one file per study in the entries directory 
This directory drives the rest of the scripts as it uses the names of the XML files 
to get the study id for the studies to migrate i.e. make sure that there aren't many files in entries during testing.

alf-upload-studies.sh - uploads the studies to alfresco (set UPDATE=false the first time you run it)

get-files.sh - downloads the files from chassis 
(this takes quite a while and is dependent on network connection - 
there's some stuff to do with checking the file sizes at the end which helps if you need to restart)

alf-upload-files.sh - upload the files to alfresco 
Need to check that all the metadata is correctly populated (in file-cmis.xsl) especially submitter

TODO
The derived from associations need to be set up 
(comments should already be done) - the syntax for the REST calls for this is:

(GET) ${ALF_HOME}/wwarn/createDerivedAssoc?outputFileNodeRef=" + outputFileNodeRef + "&studyFileNodeRef=" + studyFileNodeRef;

Because this needs the Alfresco node ref probably the right way to do this is to upload the files 
then download the feed 
(which contains the Alfresco node ref and Chassis node refs) and cross-reference with the derivations feed 
- this could probably be done by using a much simplified version of file-cmis.xsl to produce a script file and running that.
