<?xml version="1.0" encoding="utf-8"?>
<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:chassis="http://chassis.cggh.org/manta/processors"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xforms="http://www.w3.org/2002/xforms"
    xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xxforms="http://orbeon.org/oxf/xml/xforms">

    <p:param type="input" name="instance"/>
    <p:param name="data" type="output" />
    
    <p:processor name="oxf:xslt">
        <p:input name="data" href="#instance"/>
        <p:input name="config">
            <xsl:stylesheet version="1.0" xmlns:saxon="http://saxon.sf.net/" xmlns:atom="http://www.w3.org/2005/Atom">
                <xsl:template match="/">
                <parameters>
                    <format>xsd_xml</format>
                    <noredirect>true</noredirect>
                    <expanded-results>true</expanded-results>
                        <!-- e.g. 10.1128/AAC.48.10.3940-3943.2004 <xsl:value-of select="//pmid"/> -->
                    <id>doi:<xsl:value-of select="//publication-reference[@type='doi']"/></id>
                    <pid>wwarn-maps@hotmail.co.uk</pid>
                </parameters>
                </xsl:template>
            </xsl:stylesheet>
        </p:input>
        <p:output name="data" id="transformation-result"/>
    </p:processor>
    
    <p:processor name="oxf:xforms-submission">
        <p:input name="submission">
            <!-- http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi -->
            <xforms:submission method="get" content-type="text/xml" action="http://www.crossref.org/openurl/" separator="&amp;">
                <xforms:header>
                    <xforms:name>Accept</xforms:name>
                    <xforms:value>text/xml</xforms:value>
                </xforms:header>
            </xforms:submission>
            
        </p:input>
        <p:input name="request" href="#transformation-result"/>
        <p:output name="response" id="article-detail" />
    </p:processor>
    
    <p:processor name="oxf:xslt">
        <p:input name="config">
            <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xref="http://www.crossref.org/qrschema/2.0"
                version="1.0">
                <xsl:variable name="metadata" select="doc('input:article-metadata')"/>
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                <xsl:template match="//acknowledgements">
                    <acknowledgements>
                        <xsl:apply-templates select="//institutions"/>
                        <!-- Only copy from pubmed if there's one ack - hopefully first time through only -->
                        <xsl:if test="count($metadata//xref:contributor) &gt; 0">
                            <xsl:apply-templates select="$metadata//xref:contributors" />
                        </xsl:if>
                        <!-- if there's no result from crossref leave alone -->
                        <xsl:if test="count($metadata//xref:contributor) = 0">
                            <xsl:apply-templates select="person"/>
                        </xsl:if>
                    </acknowledgements>
                </xsl:template>
                <xsl:template match="xref:contributors">
                    <xsl:apply-templates select="xref:contributor" />
                </xsl:template>
                <xsl:template match="xref:contributor">
                    <person>
                        <first-name><xsl:value-of select="xref:given_name"/></first-name>
                        <middle-name></middle-name>
                        <family-name><xsl:value-of select="xref:surname"/></family-name>
                        <email-address></email-address>
                        <institution><xsl:value-of select="parent::*/xref:organization"/></institution>
                        <person-is-contactable/>
                    </person>
                </xsl:template>              
            </xsl:stylesheet>
        </p:input>
        <p:input name="data" href="#instance"/>
        <p:input name="article-metadata" href="#article-detail"/>
        <p:output name="data" ref="data" />
    </p:processor>
</p:config>