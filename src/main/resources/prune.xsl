<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:strip-space elements="*"/>
  <xsl:output indent="yes" />
  <xsl:template match="@*|node()">
    <xsl:if test=". != '' or ./@* != ''">
      <xsl:copy>
        <xsl:apply-templates  select="@*|node()"/>
      </xsl:copy>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>
