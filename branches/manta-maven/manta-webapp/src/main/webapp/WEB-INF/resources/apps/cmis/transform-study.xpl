<?xml version="1.0" encoding="utf-8"?>
<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:chassis="http://chassis.cggh.org/manta/processors"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xforms="http://www.w3.org/2002/xforms"
    xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xxforms="http://orbeon.org/oxf/xml/xforms"
    xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
    xmlns:atombeat="http://purl.org/atombeat/xmlns"
    xmlns:atom="http://www.w3.org/2005/Atom">

    <p:param type="input" name="instance"/>
    <p:param name="data" type="output" />
    
    <p:processor name="oxf:xslt">
        <p:input name="config">
            <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
                xmlns:alf="http://www.alfresco.org"
                version="1.0">
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                <xsl:template match="atombeat:member">
                    <cmis:value>
                        <xsl:apply-templates select="node()"/>
                    </cmis:value>
                </xsl:template>
                <!-- this will be for the countries -->
                <xsl:template match="atom:label">
                    <cmis:value>
                        <xsl:apply-templates select="node()"/>
                    </cmis:value>
                </xsl:template>
                <xsl:template match="alf:aspects">
                    <alf:setAspects><xsl:apply-templates/></alf:setAspects>
                </xsl:template>
                <xsl:template match="cmis:propertyString[@propertyDefinitionId='wc:modules']/text()">
                    <xsl:for-each select="tokenize(.,'\s+')">
                        <cmis:value><xsl:if test="'clinical' = .">Clinical</xsl:if>
                            <xsl:if test="'molecular' = .">Molecular</xsl:if>
                            <xsl:if test="'pharmacology' = .">Pharmacology</xsl:if>
                            <xsl:if test="'invitro' = .">In vitro</xsl:if>
                        </cmis:value>
                    </xsl:for-each>
                </xsl:template>
            </xsl:stylesheet>
        </p:input>
        <p:input name="data" href="#instance"/>
        <p:output name="data" ref="data" />
    </p:processor>
</p:config>