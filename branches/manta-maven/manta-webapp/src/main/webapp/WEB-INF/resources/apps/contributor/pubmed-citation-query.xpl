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
                    <id><xsl:apply-templates select="//pmid" /></id>
                </parameters>
                </xsl:template>
                <xsl:template match="pmid">
                    <xsl:value-of select="."/>
                    <xsl:text>,</xsl:text>
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
                <xsl:template match="//publication-title">
                    <xsl:if test="../pmid/text() = $metadata//PMID/text()">
                       <xsl:element name="publication-title">
                            <xsl:variable name="pmid" select="../pmid/text()"/>
                            <xsl:variable name="article" select="$metadata//PubmedArticle[PubmedData/ArticleIdList/ArticleId[@IdType='pubmed']/text() = $pmid]"/>
                            <xsl:apply-templates select="$article" />
                       </xsl:element>
                    </xsl:if>
                    <xsl:if test="not(../pmid/text() = $metadata//PMID/text())">
                        <xsl:element name="publication-title">
                            <xsl:apply-templates select="@*|node()"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:template>
                <xsl:template match="PubmedArticle">
                    <xsl:apply-templates select="MedlineCitation" />
                </xsl:template>
                <xsl:template match="MedlineCitation">
                    <xsl:apply-templates select="Article" />
                </xsl:template>
                <xsl:template match="Article">
                    <xsl:apply-templates select="AuthorList" />
                    <xsl:value-of select="ArticleTitle" />
                    <xsl:text> </xsl:text>
                    <xsl:if test="string-length(Journal/ISOAbbreviation) > 0">
                        <xsl:value-of select="Journal/ISOAbbreviation" />
                    </xsl:if>
                    <xsl:if test="string-length(Journal/ISOAbbreviation) = 0">
                        <xsl:value-of select="Journal/Title" />
                    </xsl:if>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="Journal/JournalIssue/PubDate/Year" />
                    <xsl:if test="string-length(Journal/JournalIssue/PubDate/Month) > 0">
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="Journal/JournalIssue/PubDate/Month" />
                    </xsl:if>
                    <xsl:if test="string-length(Journal/JournalIssue/PubDate/Day) > 0">
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="Journal/JournalIssue/PubDate/Day" />
                    </xsl:if>
                    <xsl:text>;</xsl:text>
                    <xsl:value-of select="Journal/JournalIssue/Volume" />
                    <xsl:if test="string-length(Journal/JournalIssue/Issue) > 0">
                        <xsl:text>(</xsl:text>
                        <xsl:value-of select="Journal/JournalIssue/Issue" />
                        <xsl:text>)</xsl:text>
                    </xsl:if>
                    <xsl:if test="string-length(Pagination/MedlinePgn) > 0">
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="Pagination/MedlinePgn" />
                    </xsl:if>
                    <!--
                    <xsl:text> PubMed PMID: </xsl:text><xsl:value-of select="../../PubmedData/ArticleIdList/ArticleId[@IdType='pubmed']" />
                    <xsl:if test="string-length(../../PubmedData/ArticleIdList/ArticleId[@IdType='doi']) > 0">
                        <xsl:text> DOI: </xsl:text>
                        <xsl:value-of select="../../PubmedData/ArticleIdList/ArticleId[@IdType='doi']" />
                    </xsl:if>
                    -->
                </xsl:template>
                <xsl:template match="AuthorList">
                    <xsl:apply-templates select="Author"/>
                </xsl:template>
                <xsl:template match="Author">
                    <xsl:value-of select="LastName"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="Initials"/>
                    <xsl:if test="count(following-sibling::Author) > 0">
                        <xsl:text>, </xsl:text>
                    </xsl:if>
                    <xsl:if test="count(following-sibling::Author) = 0">
                        <xsl:text>. </xsl:text>
                    </xsl:if>
                </xsl:template>
                
            </xsl:stylesheet>
        </p:input>
        <p:input name="data" href="#instance"/>
        <p:input name="article-metadata" href="#article-detail"/>
        <p:output name="data" ref="data" />
    </p:processor>
</p:config>