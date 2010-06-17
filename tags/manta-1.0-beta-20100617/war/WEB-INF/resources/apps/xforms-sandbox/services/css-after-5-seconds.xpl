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
    xmlns:oxf="http://www.orbeon.com/oxf/processors"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:thread="java.lang.Thread">

    <p:processor name="oxf:unsafe-xslt">
        <p:input name="data"><dummy/></p:input>
        <p:input name="config">
            <document xsi:type="xs:string" content-type="text/css" xsl:version="2.0">
                <!-- The CSS we send back to the browser -->
                <xsl:text>.my-class { background-color: green }</xsl:text>
                <!-- Prevent pipeline engine to cache the output of this processor -->
                <xsl:if test="false()">
                    <xsl:value-of select="doc('http://dummy')"/>
                </xsl:if>
                <!-- Wait for 5 seconds -->
                <xsl:value-of select="thread:sleep(5000)"/>
            </document>
        </p:input>
        <p:output name="data" id="css"/>
    </p:processor>

    <p:processor name="oxf:http-serializer">
        <p:input name="config">
            <config/>
        </p:input>
        <p:input name="data" href="#css"/>
    </p:processor>


</p:config>
