<!--
    Copyright (C) 2006-2007 Orbeon, Inc.

    This program is free software; you can redistribute it and/or modify it under the terms of the
    GNU Lesser General Public License as published by the Free Software Foundation; either version
    2.1 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
    without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Lesser General Public License for more details.

    The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
-->
<!--
    This is a very simple theme that shows you how to create a common layout for all your pages. You can modify it at
    will or, even better, copy it as theme-[yourapp].xsl and refer to the new name from the epilogue pipeline.
-->
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:xhtml="http://www.w3.org/1999/xhtml"
    xmlns:version="java:org.orbeon.oxf.common.Version"
    xmlns:xxforms="http://orbeon.org/oxf/xml/xforms">

    <!-- Orbeon Forms version -->
    <xsl:variable name="orbeon-forms-version" select="version:getVersion()" as="xs:string"/>

    <!-- - - - - - - Themed page template - - - - - - -->
    <xsl:template match="/*">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xhtml:head>
            
                <!-- Add meta as early as possible -->
                <xsl:apply-templates select="/xhtml:html/xhtml:head/xhtml:meta"/>
                
                <!-- Handle head elements except scripts -->
                <xsl:apply-templates select="/xhtml:html/xhtml:head/(xhtml:link | xhtml:style)"/>
                
                <!-- Title -->
                <xhtml:title>
                    <xsl:apply-templates select="/xhtml:html/xhtml:head/xhtml:title/@*"/>
                    <xsl:choose>
                        <xsl:when test="/xhtml:html/xhtml:head/xhtml:title != ''">
                            <xsl:value-of select="/xhtml:html/xhtml:head/xhtml:title"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="(/xhtml:html/xhtml:body/xhtml:h1)[1]"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xhtml:title>

                <!-- NOTE: The XForms engine may place additional scripts and stylesheets here as needed -->
                <xhtml:link rel="stylesheet" href="/config/theme/orbeon.css" type="text/css" media="all"/>
                <xhtml:link rel="stylesheet" href="/apps/common/theme/manta.css" type="text/css" media="all"/>
                <xhtml:link rel="stylesheet" href="/apps/common/theme/wwarn.css" type="text/css" media="all"/>
				<xhtml:script type="text/javascript" src="/apps/common/theme/js/jquery-1.4.2.min.js"></xhtml:script>
			    
                <!-- Orbeon Forms version -->
                <xhtml:meta name="generator" content="Orbeon Forms {$orbeon-forms-version}"/>
                
                <!-- Favicon -->
                <!-- TODO: Replace this icon 
                <xhtml:link rel="shortcut icon" href="/ops/images/orbeon-icon-16.ico"/>
                <xhtml:link rel="icon" href="/ops/images/orbeon-icon-16.png" type="image/png"/>
                 -->
                
            </xhtml:head>
            
            <xhtml:body>
                <!-- Copy body attributes -->
                <xsl:apply-templates select="/xhtml:html/xhtml:body/@*"/>
                
                <xhtml:div class="header">
					<xhtml:h1 class="logo"><a href="index.html">WWARN - Worldwide Antimalarial Resistance Network</a></xhtml:h1>
					
				    <xhtml:div id="block-menu-primary-links">
					    <xhtml:ul id="primary" class="clearfix">
					        <xhtml:li class="current"><a href="#nogo">Home</a></xhtml:li>
					        <xhtml:li><a href="#nogo">About Us</a></xhtml:li>
					        <xhtml:li><a href="#nogo">Supporting Research</a></xhtml:li>
					        <xhtml:li><a href="#nogo">Contributing Data</a></xhtml:li>
					        <xhtml:li><a href="#nogo">Tracking Resistance</a></xhtml:li>
					        <xhtml:li><a href="#nogo">Community</a></xhtml:li>
					        <xhtml:li><a href="#nogo">News &amp; Media</a></xhtml:li>
					    </xhtml:ul>
				    </xhtml:div>
				    
					<div class="secondary-wrap">
					
					</div>
				</xhtml:div>
				
                <xhtml:div id="holdall">
                	<xhtml:div id="main-inner">
                		<xhtml:div class="content page">
                	
		                	<xhtml:div id="hd">
		                		<!-- <xhtml:h1>Chassis DSN Data Management</xhtml:h1>  -->
		                	</xhtml:div>
		
		                	<xhtml:div id="bd" class="clearfix">
								<xhtml:div class="sidebar" id="sidebar-left">
								
								</xhtml:div>
				                <!-- Copy body -->
				                <xhtml:div class="main-indent no-bg">
				                	<xhtml:div class="main twoCol">
					                	<xsl:apply-templates select="/xhtml:html/xhtml:body/node()"/>
				                	</xhtml:div>
				                </xhtml:div>
		                	</xhtml:div>
		                	<xhtml:img src="http://wwarn.kmp.co.uk/sites/default/files/imagefield_default_images/default.jpg" class="background" alt=""></xhtml:img>
                		</xhtml:div> 
                	</xhtml:div>
                </xhtml:div>
                
               	<xhtml:div id="ft">
               		<!-- <xhtml:p>Chassis Manta 1.0-alpha-SNAPSHOT</xhtml:p> -->
               		<xhtml:div id="footer-inner">
					    <xhtml:div class="block">
					        <xhtml:ul class="menu">
					            <xhtml:li><xhtml:a title="" href="/contact">Contact Us</xhtml:a></xhtml:li>
					            <xhtml:li><xhtml:a title="" href="/sitemap">Sitemap</xhtml:a></xhtml:li>
					            <xhtml:li><xhtml:a title="" href="/terms-use">Terms &#38; Conditions</xhtml:a></xhtml:li>
					            <xhtml:li><xhtml:a title="" href="/site-credits">Site Credits</xhtml:a></xhtml:li>
					        </xhtml:ul>
					    </xhtml:div>
					    <xhtml:div class="block">
					        <xhtml:p><xhtml:a href="http://wwarn.org">&#169;2010 WWARN</xhtml:a></xhtml:p>
					    </xhtml:div>
					</xhtml:div>
               	</xhtml:div>
               	
            </xhtml:body>
            
            <!-- Scripts at the bottom of the page. This is not valid HTML, but it is a recommended practice for
                 performance as of early 2008. See http://developer.yahoo.com/performance/rules.html#js_bottom -->
            <xsl:for-each select="/xhtml:html/xhtml:head/xhtml:script">
                <xsl:element name="xhtml:{local-name()}" namespace="{namespace-uri()}">
                    <xsl:apply-templates select="@*|node()"/>
                </xsl:element>
            </xsl:for-each>
        </xsl:copy>
    </xsl:template>

    <!-- Simply copy everything that's not matched -->
    <xsl:template match="@*|node()" priority="-2">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
