#Produces a list of publication files from CHASSIS together with a list of the PMIDs associated with that study e.g.
#PCGTT,Nosten et al Cardiotoxicity_Lancet 1993.pdf,PMID:8096956,PMID:8096959
#PCGTT,ter Kuile et al_Halofantrine_Lancet 1993.pdf,PMID:8096956,PMID:8096959
. ./config.sh

./get-studies.sh

#See get-files.sh - Note not calling get-files.sh as that downloads the actual files themselves
#For Alfresco can submit a CMIS query - will need to modify publications.xsl
for i in curated.xml submitted.xml
do
	java -classpath ${CLASSPATH} org.apache.xalan.xslt.Process -IN $i -XSL publications.xsl 
done

