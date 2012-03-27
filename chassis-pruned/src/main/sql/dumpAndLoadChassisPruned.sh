#!/bin/bash 

# TPP 2012-02-28
# This script is intended to be run after chassisPruned has been rebuilt.
# It assumes that the database will be on this machine, so does not play nice on saturn/charlie. 

# Note that it relies upon the ssh keys for jenkins being already installed on reporting.wwarn.org

set -o errexit

if [ ! $1 ]; then 
  echo "Specify a user and password for wwarn.org"
  exit 1
fi

export user=$1
export passwd=$2

export out=`date "+%Y%m%d"`

export file=chassisPruned_$out.sql

mysqldump -u root chassisPruned  --complete-insert --skip-opt --add-drop-table  > $file

cp $file chassisPruned.sql 

scp $file uploader@reporting.wwarn.org:/home/uploader/

#curl --upload-file chassisDb.sql --user $user:$passwd -s -S \
# https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/b4fc79d3-3160-4f88-a7ea-82fcb6f3017e/content.sql

curl --upload-file chassisPruned.sql   --user $user:$passwd -s -S \
  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/08a2d557-d32f-4c67-8672-bbdfea76ac9b/content.sql

rm chassisPruned.sql
rm $file

curl --upload-file README.txt   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/e7ee0c7c-bb2a-4633-b82c-50a9700dd7a0/contentsREADME.txt

rm -f allStudies.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0A%29+s%0D%0AORDER+BY+s.StudyId%0D%0A&filename=allStudies.csv&Run=Run&csv=on"
curl --upload-file allStudies.csv            --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/ef54ef3c-fc8a-4bf5-a03e-271ebe8b706d/content.csv

rm -f clinicalStudies.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25clinical%25%22%0D%0A%29+s%0D%0AORDER+BY+s.StudyId%0D%0A&filename=clinicalStudies.csv&Run=Run&csv=on"
curl --upload-file clinicalStudies.csv       --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/603ac1d0-0423-4d9f-a15c-49462e57257d/contentsclinicalStudies.csv

rm -f invitroStudies.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25invitro%25%22%0D%0A%29+s%0D%0AORDER+BY+s.StudyId%0D%0A&filename=invitroStudies.csv&Run=Run&csv=on"
curl --upload-file invitroStudies.csv        --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/44ae8543-0d37-4db6-9427-1eba5ec12ddf/contentsinvitroStudies.csv

rm -f molecularStudies.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25molecular%25%22%0D%0A%29+s%0D%0AORDER+BY+s.StudyId%0D%0A&filename=molecularStudies.csv&Run=Run&csv=on"
curl --upload-file molecularStudies.csv      --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/7f123848-5f58-4123-af22-9ca29e6c78b1/contentsmolecularStudies.csv

rm -f pharmacologyStudies.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25pharmacology%25%22%0D%0A%29+s%0D%0AORDER+BY+s.StudyId%0D%0A&filename=pharmacologyStudies.csv&Run=Run&csv=on"
curl --upload-file pharmacologyStudies.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/ba73c285-3d42-48dc-821f-ae5f55c9e237/contentspharmacologyStudies.csv




rm -f allStudySites.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=allStudySites.csv&Run=Run&csv=on"
curl --upload-file allStudySites.csv            --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/d139e9c7-1c2e-4e57-9f88-7b4c10aa4a66/contentsallStudySites.csv

rm -f clinicalStudySites.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25clinical%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=clinicalStudySites.csv&Run=Run&csv=on"
curl --upload-file clinicalStudySites.csv       --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/d832d87a-fe65-449a-9c3b-219604f0271e/contentsclinicalStudySites.csv

rm -f invitroStudySites.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25invitro%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=invitroStudySites.csv&Run=Run&csv=on"
curl --upload-file invitroStudySites.csv        --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/2c7cfec8-ba90-498b-afbe-739762ca338e/contentsinvitroStudySites.csv

rm -f molecularStudySites.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25molecular%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=molecularStudySites.csv&Run=Run&csv=on"
curl --upload-file molecularStudySites.csv      --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/85393f91-1b3c-477e-9252-4f4146b5730a/contentsmolecularStudySites.csv

rm -f pharmacologyStudySites.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++s.StartDate%2C+%0D%0A+++s.EndDate%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25pharmacology%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=pharmacologyStudySites.csv&Run=Run&csv=on"
curl --upload-file pharmacologyStudySites.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/466b6b84-8a5d-45fc-83e6-eda007d5c639/content.csv



rm -f allStudySitesPublications.csv
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%2C%0D%0A+++Publication.PublicationTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%2C%0D%0A+++Study.Publications_Study_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0Aleft+join+Publications+on++s.Publications_Study_Hjid+%3D+Publications.Hjid+%0D%0Aleft+join+Publication+on+Publication.Publication_Publications_Hjid+%3D+Publications.Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=allStudySitesPublications.csv&Run=Run&csv=on"
curl --upload-file allStudySitesPublications.csv   --user $user:$passwd -s -S https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/0ba53274-0609-419c-b955-31c3b39abf10/content.csv 

rm -f clinicalStudySitesPublications.csv 
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%2C%0D%0A+++Publication.PublicationTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%2C%0D%0A+++Study.Publications_Study_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25clinical%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0Aleft+join+Publications+on++s.Publications_Study_Hjid+%3D+Publications.Hjid+%0D%0Aleft+join+Publication+on+Publication.Publication_Publications_Hjid+%3D+Publications.Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=clinicalStudySitesPublications.csv&Run=Run&csv=on"
curl --upload-file clinicalStudySitesPublications.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/acc616a9-5813-4fda-ba3e-064456fa72fc/content.csv

rm -f invitroStudySitesPublications.csv 
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%2C%0D%0A+++Publication.PublicationTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%2C%0D%0A+++Study.Publications_Study_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25invitro%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0Aleft+join+Publications+on++s.Publications_Study_Hjid+%3D+Publications.Hjid+%0D%0Aleft+join+Publication+on+Publication.Publication_Publications_Hjid+%3D+Publications.Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=invitroStudySitesPublications.csv&Run=Run&csv=on"
curl --upload-file invitroStudySitesPublications.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/542e3fe4-bda9-4ccd-bcb1-dcf3d69dc908/content.csv

rm -f molecularStudySitesPublications.csv 
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%2C%0D%0A+++Publication.PublicationTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%2C%0D%0A+++Study.Publications_Study_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25molecular%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0Aleft+join+Publications+on++s.Publications_Study_Hjid+%3D+Publications.Hjid+%0D%0Aleft+join+Publication+on+Publication.Publication_Publications_Hjid+%3D+Publications.Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=molecularStudySitesPublications.csv&Run=Run&csv=on"
curl --upload-file molecularStudySitesPublications.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/619fdd20-f1a6-4095-b184-f9ff8ae1b1be/content.csv

rm -f pharmacologyStudySitesPublications.csv 
curl -O --remote-header-name "http://localhost:8080/posql/selection/?db=chassisPruned&query=select+%0D%0A+++s.StudyId%2C+%0D%0A+++s.PublishedItem+as+DateReceived%2C+%0D%0A+++s.Modules%2C+%0D%0A+++Site.LookupAddress+as+Site%2C%0D%0A+++Site.Population%2C%0D%0A+++Site.Latitude%2C%0D%0A+++Site.Longitude%2C%0D%0A+++s.StudyTitle%2C%0D%0A+++Publication.PublicationTitle%0D%0A+from+%0D%0A%28+select+%0D%0A+++Entry.StudyId%2C+%0D%0A+++Entry.PublishedItem%2C+%0D%0A+++Study.Modules%2C+%0D%0A+++StudyInfo.StartDate%2C+%0D%0A+++StudyInfo.EndDate%2C%0D%0A+++Study.StudyTitle%2C%0D%0A+++StudyInfo.Sites_StudyInfo_Hjid%2C%0D%0A+++Study.Publications_Study_Hjid%0D%0Afrom+%0D%0AEntry%2C+Content%2C+Study%2C+StudyInfo%0D%0Awhere%0D%0A+Entry.Content_Entry_Hjid+%3D+Content.Hjid+and%0D%0A+Content.Study_Content_Hjid+%3D+Study.Hjid+and%0D%0A+Study.StudyInfo_Study_Hjid+%3D+StudyInfo.Hjid+%0D%0Aand+Study.Modules+like+%22%25pharmacology%25%22%0D%0A%29+s%0D%0Aleft+join+Sites+on+s.Sites_StudyInfo_Hjid+%3D+Sites.Hjid+%0D%0Aleft+join+Site+on+Sites.Hjid+%3D+Site.Site_Sites_Hjid+%0D%0Aleft+join+Publications+on++s.Publications_Study_Hjid+%3D+Publications.Hjid+%0D%0Aleft+join+Publication+on+Publication.Publication_Publications_Hjid+%3D+Publications.Hjid+%0D%0AORDER+BY+s.StudyId%0D%0A&filename=pharmacologyStudySitesPublications.csv&Run=Run&csv=on"
curl --upload-file pharmacologyStudySitesPublications.csv   --user $user:$passwd -s -S  https://www.wwarn.org/alfresco/service/cmis/s/workspace:SpacesStore/i/e7cc4428-71dc-4e56-9bb2-dbcffd29a8e5/content.csv



