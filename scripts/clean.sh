. ./config.sh
rm ${COOKIES}
rm ${STUDIES}
rm -r ${STUDIES_DIR}
#This is hard coded in file-association.xsl and file-cmis.xsl
rm derivations.xml
rm -r derivations

rm curated.xml
rm -r curated

rm submitted.xml
rm -r submitted

rm -r cmis-folders
rm -r cmis-files
rm -r cmis-entries
#Do not delete as it takes a long time to download all the files
#It doesn't matter if there are extra files in the directory
#rm -r files
rm foo.out
rm studies.ticket