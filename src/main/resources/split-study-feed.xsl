<xsl:stylesheet extension-element-prefixes="redirect"
	xmlns:atom="http://www.w3.org/2005/Atom" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:manta="http://www.cggh.org/2010/chassis/manta/xmlns"
	xmlns:redirect="http://xml.apache.org/xalan/redirect" version="1.0">
	<xsl:output method="xml" />
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>
	<xsl:template match="atom:feed">
		<xsl:apply-templates select="atom:entry" />
	</xsl:template>
	<xsl:template match="atom:entry">
		<xsl:variable name="filename" select="concat('studies/',manta:id/text(),'.xml')" />
		<redirect:write select="$filename">
			<atom:entry>
				<xsl:apply-templates />
			</atom:entry>
		</redirect:write>
	</xsl:template>
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>