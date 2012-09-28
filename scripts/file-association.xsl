<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/"
	xmlns:alf="http://www.alfresco.org" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:atombeat="http://purl.org/atombeat/xmlns"
	xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" version="1.0">
	<xsl:output method="text" />
	<xsl:variable name="derivations" select="document('derivations.xml')"/>
	<xsl:variable name="inputDoc" select="/"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="//cmis:value"/>
	</xsl:template>
	
	
	<xsl:template match="//cmis:value">
		<xsl:if test="'wc:fileId' = ../@queryName">
			<xsl:variable name="ref" select="../../../../cmis:propertyId[@queryName='cmis:objectId']/cmis:value"/>
			<xsl:variable name="me" select="text()"/>
			<xsl:for-each select="$derivations//atom:link[@href=$me and @rel='http://www.cggh.org/2010/chassis/terms/derivationInput']/parent::*">
				<xsl:for-each select="atom:link[@rel='http://www.cggh.org/2010/chassis/terms/derivationOutput']">
					<xsl:variable name="href" select="@href"/>
					<xsl:text>/wwarn/createDerivedAssoc?outputFileNodeRef=</xsl:text>
					<xsl:value-of select="$ref"/>
					<xsl:text>&amp;studyFileNodeRef=</xsl:text>
					<xsl:value-of select="$inputDoc//cmis:value[. = $href]/../../../../cmis:propertyId[@queryName='cmis:objectId']/cmis:value"/>
					<xsl:text>&#10;</xsl:text>
				</xsl:for-each>
			</xsl:for-each>
			
		</xsl:if>
	</xsl:template>
	
	
</xsl:stylesheet>