<!--
    Copyright (C) 2009 Orbeon, Inc.

    This program is free software; you can redistribute it and/or modify it under the terms of the
    GNU Lesser General Public License as published by the Free Software Foundation; either version
    2.1 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
    without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Lesser General Public License for more details.

    The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
-->
<p:config xmlns:p="http://www.orbeon.com/oxf/pipeline"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:svg="http://www.w3.org/2000/svg"
    xmlns:xhtml="http://www.w3.org/1999/xhtml"
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:xforms="http://www.w3.org/2002/xforms"
    xmlns:xxforms="http://orbeon.org/oxf/xml/xforms"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom">

    <p:param type="input" name="instance"/>
    
    
    
    <!-- set up a request to retrieve the study -->
    
    <p:processor name="oxf:xslt">
        <p:input name="config">
            <xsl:stylesheet version="2.0">
                <xsl:template match="/">
                    <xsl:variable name="uri" select="//study"/>
                    <request uri="{$uri}">
                    </request>
                </xsl:template>
            </xsl:stylesheet>                        
        </p:input>
        <p:input name="data" href="#instance"/>
        <p:output name="data" id="study-request"/>
    </p:processor>
    
    
    
    <!-- now retrieve the study -->
    
    <p:processor name="oxf:xforms-submission">
        <p:input name="submission">
            <xforms:submission method="get" action="{@uri}"/>
        </p:input>
        <p:input name="request" href="#study-request"/>
        <p:output name="response" id="study-response"/>        
    </p:processor>
    
    
    
    <!-- now start posting files -->
    
    <p:for-each href="#instance" select="//file/location" root="atom:feed" id="media-feed">
        
        <p:processor name="oxf:xforms-submission">
            <p:input name="submission">
                <xforms:submission 
                    method="post" 
                    action="http://localhost:8081/manta/atombeat/content/media"
                    mediatype="{@mediatype}"
                    serialization="application/octet-stream"/>
            </p:input>
            <p:input name="request" href="current()"/>
            <p:output name="response" ref="media-feed"/>
        </p:processor>
        
    </p:for-each>



    <!-- output XML -->
    
    <p:processor name="oxf:xml-converter">
        <p:input name="config">
            <config>
                <method>xml</method>
                <indent>true</indent>
                <indent-amount>4</indent-amount>
            </config>
        </p:input>
        <p:input name="data" href="#media-feed"/>
        <p:output name="data" id="converted"/>
    </p:processor>
    
    
    
    <!-- Serialize to HTTP -->
    
    <p:processor name="oxf:http-serializer">
        <p:input name="config">
            <config>
                <!-- NOTE: converter specifies text/html content-type -->
            </config>
        </p:input>
        <p:input name="data" href="#converted"/>
    </p:processor>



</p:config>
