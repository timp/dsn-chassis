<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/"
	xmlns:alf="http://www.alfresco.org" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:atombeat="http://purl.org/atombeat/xmlns"
	xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" version="1.0">
	<xsl:output method="xml" />
	<xsl:variable name="countries" select="document('../src/main/webapp/WEB-INF/resources/apps/common/constants/countries.xml')/countries"/>
	<xsl:template match="atom:entry">
		<atom:entry>
		<atom:title>
		<xsl:apply-templates select="manta:id/text()" /></atom:title>
		<cmisra:object>
			<cmis:properties>
				<cmis:propertyId propertyDefinitionId="cmis:objectTypeId">
					<cmis:value>F:wc:studyFolder</cmis:value>
				</cmis:propertyId>
				<alf:setAspects>
					<alf:appliedAspects>P:cm:titled</alf:appliedAspects>
					<alf:appliedAspects>P:wc:workflowInfos</alf:appliedAspects>
					<alf:appliedAspects>P:wc:studyFolderData</alf:appliedAspects>
					<alf:properties>
						<cmis:propertyString propertyDefinitionId="cm:description"
							displayName="Description" queryName="cm:description">
							<cmis:value></cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="cm:title"
							displayName="Title" queryName="cm:title">
							<cmis:value>
								<xsl:apply-templates select="//studyTitle/text()" />
							</cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:modules"
							displayName="The module(s) that the file belongs to" queryName="wc:modules">
							<xsl:apply-templates select="//modules" />
						</cmis:propertyString>

						<cmis:propertyString propertyDefinitionId="wc:admins"
							displayName="Users (first name and last name) that are the creators of this study (admins)"
							queryName="wc:admins" ><xsl:apply-templates select="//atombeat:member" /></cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:countries"
							displayName="The country/countries where the study originated"
							queryName="wc:countries">
							<xsl:apply-templates select="//country" />
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:studyInfoLink"
							displayName="Link to the study metadata XML in Chassis"
							queryName="wc:studyInfoLink">
							<cmis:value>
							<xsl:apply-templates select="//atom:link" />
							</cmis:value>
						</cmis:propertyString>
						<cmis:propertyString propertyDefinitionId="wc:region"
							displayName="The region where the study originated" queryName="wc:region" />

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
	
	<xsl:template match="//modules">
	<!-- Note doesn't support \s+ -->
		<xsl:for-each select="str:tokenize(.,' ')">
			<cmis:value>
				<xsl:if test="'clinical' = .">Clinical</xsl:if>
				<xsl:if test="'molecular' = .">Molecular</xsl:if>
				<xsl:if test="'pharmacology' = .">Pharmacology</xsl:if>
				<xsl:if test="'invitro' = .">In vitro</xsl:if>
			</cmis:value>
		</xsl:for-each>
		
	</xsl:template>
	
	<xsl:template match="//country">
	<xsl:variable name="code" select="."/>
	<cmis:value>
		<xsl:value-of select="$countries/country[value=$code]/label"/>
	</cmis:value>
	</xsl:template>
	<xsl:template match="//atombeat:member">
	
     <cmis:value>
       <xsl:apply-templates select="node()"/>
     </cmis:value>
    
  </xsl:template>
</xsl:stylesheet>