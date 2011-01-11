<?xml version="1.0" encoding="utf-8"?>
<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:chassis="http://chassis.cggh.org/manta/processors"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xforms="http://www.w3.org/2002/xforms"
    xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xxforms="http://orbeon.org/oxf/xml/xforms">

    <p:param type="input" name="instance"/>
    <p:param name="data" type="output" debug="out"/>
    
    <p:processor name="oxf:xslt">
        <p:input name="data" href="#instance"/>
        <p:input name="config">
            <xsl:stylesheet version="1.0" xmlns:saxon="http://saxon.sf.net/" xmlns:atom="http://www.w3.org/2005/Atom">
                <xsl:template match="/">
                    <message>
                        <credentials>
                            <username jndi-ref="smtp-user"></username>
                            <password jndi-ref="smtp-password"></password>
                        </credentials>
                        <from>
                            <email jndi-ref="smtp-from-email"></email>
                            <name jndi-ref="smtp-from-name"></name>
                        </from>
                        <to>
                            <email><xsl:value-of select="/atom:entry/atom:author/atom:email"/></email>
                        </to>
                        <subject>Finished making changes to study <xsl:value-of select="/atom:entry/atom:title"/> has been updated</subject>
                        <body content-type="text/plain">
                            <xsl:value-of select="/atom:entry/atom:title"/> 
                        </body>
                    </message>
                </xsl:template>
            </xsl:stylesheet>
        </p:input>
        <p:output name="data" id="transformation-result"/>
    </p:processor>
    
    <p:processor name="chassis:semail">
        <p:input name="data" href="#transformation-result" />
    </p:processor>
    
    <p:processor name="oxf:xslt">
        <p:input name="data" href="#instance"/>
        <p:input name="config">
            <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl"
                exclude-result-prefixes="xd"
                version="1.0">
                <xd:doc scope="stylesheet">
                    <xd:desc>
                        <xd:p><xd:b>Created on:</xd:b> Dec 10, 2010</xd:p>
                        <xd:p><xd:b>Author:</xd:b> iwright</xd:p>
                        <xd:p></xd:p>
                    </xd:desc>
                </xd:doc>
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                <xsl:template match="//study-status">
                    <study-status>in</study-status>
                </xsl:template>
            </xsl:stylesheet>
        </p:input>
        <p:output name="data" ref="data" />
    </p:processor>
</p:config>