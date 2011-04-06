<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:xref="http://www.crossref.org/qrschema/2.0"
                xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl"
                exclude-result-prefixes="xd"
                version="1.0">
                <xsl:variable name="metadata" select="document('crossref.xml')"/>
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                <xsl:template match="//acknowledgements">
                    <acknowledgements>
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
                        <middle-name><xsl:value-of select="xref:Initials"/></middle-name>
                        <family-name><xsl:value-of select="xref:surname"/></family-name>
                        <email-address></email-address>
                        <institution><xsl:value-of select="parent::*/xref:organization"/></institution>
                        <person-is-contactable/>
                    </person>
                </xsl:template>             
            </xsl:stylesheet>