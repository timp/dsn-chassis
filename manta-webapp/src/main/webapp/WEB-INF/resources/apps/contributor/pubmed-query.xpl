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
                    <db>pubmed</db>
                    <retmode>xml</retmode>
                    <!-- e.g. 15388456 <xsl:value-of select="//pmid"/> -->
                    <id><xsl:value-of select="//pmid"/></id>
                </parameters>
                </xsl:template>
            </xsl:stylesheet>
        </p:input>
        <p:output name="data" id="transformation-result"/>
    </p:processor>
    
    <p:processor name="oxf:xforms-submission">
        <p:input name="submission">
            <!-- /ChangeContentType?id= - won't work until pubmed fix their Content-Type header to return text/xml or similar -->
            <!-- http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi -->
            <xforms:submission method="get" content-type="text/xml" action="/ChangeContentType" separator="&amp;">
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
                version="1.0">
                <xsl:variable name="metadata" select="doc('input:article-metadata')"/>
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                <xsl:template match="//acknowledgements">
                    <acknowledgements>
                        <institutions>
                            <institution-ack>
                                <institution-name><xsl:value-of select="$metadata//Affiliation"/></institution-name>
                            </institution-ack>
                        </institutions>
                        <!-- Only copy from pubmed if there's a valid response -->
                        <xsl:if test="count($metadata//Article/AuthorList/Author) &gt; 0">
                            <xsl:apply-templates select="$metadata//Article" />
                        </xsl:if>
                        <!-- if there's no result from pubmed leave alone -->
                        <xsl:if test="count($metadata//Article/AuthorList/Author) = 0">
                            <xsl:apply-templates select="person"/>
                        </xsl:if>
                    </acknowledgements>
                </xsl:template>
                <xsl:template match="Article">
                    <xsl:apply-templates select="AuthorList" />
                </xsl:template>
                <xsl:template match="AuthorList">
                        <xsl:apply-templates select="Author"/>
                </xsl:template>
                <xsl:template match="Author">
                    <person>
                        <first-name><xsl:value-of select="ForeName"/></first-name>
                        <middle-name></middle-name>
                        <family-name><xsl:value-of select="LastName"/></family-name>
                        <email-address></email-address>
                        <institution></institution>
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