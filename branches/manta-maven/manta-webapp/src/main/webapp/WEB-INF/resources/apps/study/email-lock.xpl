<?xml version="1.0" encoding="utf-8"?>
<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:chassis="http://chassis.cggh.org/manta/processors"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xforms="http://www.w3.org/2002/xforms"
    xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xxforms="http://orbeon.org/oxf/xml/xforms">

    <p:param type="input" name="instance"/>
    
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
                        <subject>Your study <xsl:value-of select="/atom:entry//studyTitle"/> has been locked</subject>
                        <body content-type="text/plain">
                            Your study <xsl:value-of select="/atom:entry//studyTitle"/> has now entered the WWARN curation process.
                            If you wish to make any changes then you must contact us first
                            
                            Thank you for your submission.
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
</p:config>