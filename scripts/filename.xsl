<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/"
	xmlns:alf="http://www.alfresco.org" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:atombeat="http://purl.org/atombeat/xmlns"
	xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" version="1.0">
	<xsl:output method="text" />
	
	<xsl:template match="/">
		<xsl:apply-templates select="//atom:link"/>
		<xsl:apply-templates select="//atom:title"/>
	</xsl:template>
	
	<xsl:template match="//atom:link">
		<xsl:if test="@rel='http://www.cggh.org/2010/chassis/terms/originStudy'">
			<xsl:variable name="studyRef" select="@href"/>
			<xsl:value-of select="@manta:idref"/>
			<xsl:text>/</xsl:text>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="//atom:title">
				<xsl:value-of select="."/>
	</xsl:template>
	
	
</xsl:stylesheet>