<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/"
	xmlns:alf="http://www.alfresco.org" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:atombeat="http://purl.org/atombeat/xmlns"
	xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" version="1.0">
	<xsl:output method="xml" />
	<xsl:variable name="derivations" select="document('derivations.xml')"/>
	<xsl:template match="atom:entry">
		<atom:entry>
		<xsl:apply-templates select="atom:title" />
		<atom:content/>
		<cmisra:object>
			<cmis:properties>
				<cmis:propertyId propertyDefinitionId="cmis:objectTypeId">
					<cmis:value>D:<xsl:apply-templates select="atom:category"/></cmis:value>
				</cmis:propertyId>
				<alf:setAspects>
					<alf:appliedAspects>P:cm:titled</alf:appliedAspects>
					<alf:appliedAspects>P:wc:derivations</alf:appliedAspects>
					<alf:appliedAspects>P:wc:fileData</alf:appliedAspects>
					<alf:properties>
						<cmis:propertyString propertyDefinitionId="cm:description"
							displayName="Description" queryName="cm:description">
							<cmis:value>
								<xsl:apply-templates mode="other" select="atom:category"/>
							</cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="cm:title"
							displayName="Title" queryName="cm:title">
							<cmis:value>
								<xsl:apply-templates select="atom:title/text()" />
							</cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:fileSource"
							displayName="File Source" queryName="wc:fileSource">
							<cmis:value>Contributor</cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:submitter" displayName="Submitter"
 							queryName="wc:submitter">
 							<cmis:value><xsl:apply-templates select="atom:author/atom:email/text()" /></cmis:value>
 						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:fileId"
							displayName="File ID" queryName="wc:fileId">
							<cmis:value><xsl:apply-templates select="atom:id/text()" /></cmis:value>
						</cmis:propertyString>
						<xsl:apply-templates mode="derivations" select="atom:link"/>
					</alf:properties>
				</alf:setAspects>

			</cmis:properties>
		</cmisra:object>
		</atom:entry>
		
	</xsl:template>


	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="//atom:link">
		<xsl:if test="'self' = @rel">
		<xsl:value-of select="@href"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template mode="derivations" match="//atom:link">
		<xsl:if test="'self' = @rel">
			<xsl:variable name="me" select="@href"/>
			<cmis:propertyString propertyDefinitionId="wc:derivationsComments" displayName="Derivations comment" queryName="wc:derivationsComments">
			<xsl:for-each select="$derivations//atom:link[@href=$me and @rel='http://www.cggh.org/2010/chassis/terms/derivationInput']/parent::*">
				<cmis:value><xsl:value-of select="atom:summary/text()"/></cmis:value>
			</xsl:for-each>
			</cmis:propertyString>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="atom:category">
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/Protocol' = @term">wc:protocol</xsl:if>
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/Publication' = @term">wc:publication</xsl:if>
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/DataFile' = @term">wc:dataFile</xsl:if>
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/Other' = @term">wc:other</xsl:if>
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/DataDictionary' = @term">wc:dataDictionary</xsl:if>
				<xsl:if test="'http://www.cggh.org/2010/chassis/terms/Explorer' = @term">wc:explorerData</xsl:if>
	</xsl:template>

	<xsl:template mode="other" match="atom:category">
				<xsl:value-of select="@label"/>
	</xsl:template>
	
	<xsl:template match="atom:id">
		<xsl:for-each select="str:tokenize(.,'/')">
			<xsl:if test="'submitted' = .">Contributor</xsl:if>
			<xsl:if test="'curated' = .">Curator</xsl:if>
		</xsl:for-each>
	</xsl:template>	
</xsl:stylesheet>