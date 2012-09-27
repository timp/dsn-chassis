<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/"
	xmlns:alf="http://www.alfresco.org" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:atombeat="http://purl.org/atombeat/xmlns"
	xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" version="1.0">
	<xsl:output method="text" />
	<xsl:variable name="studies" select="document('studies.xml')"/>
	<xsl:variable name="inputDoc" select="/"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="//atom:category"/>
	</xsl:template>
	
	<xsl:template match="//atom:category">
		<xsl:if test="@term='http://www.cggh.org/2010/chassis/terms/Publication'">
			<xsl:variable name="studyRef" select="../atom:link[@rel='http://www.cggh.org/2010/chassis/terms/originStudy']/@href"/>
			<xsl:value-of select="../atom:link[@rel='http://www.cggh.org/2010/chassis/terms/originStudy']/@manta:idref"/>
			<xsl:text>,</xsl:text>
			<xsl:value-of select="../atom:title"/>
			<xsl:for-each select="$studies//atom:link[@href=$studyRef and @rel='self']/parent::*//pmid">
				<xsl:text>,PMID:</xsl:text>	
				<xsl:value-of select="."/>
			</xsl:for-each>
			<xsl:text>&#10;</xsl:text>
		</xsl:if>
	</xsl:template>
	
	
</xsl:stylesheet>