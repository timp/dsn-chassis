<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:atom="http://www.w3.org/2005/Atom">
	<xsl:template match="*">
<xsl:copy>
<xsl:copy-of select="@*"/>
<xsl:apply-templates/>
</xsl:copy>
</xsl:template>

<!-- Remove node by ignoring -->
<xsl:template match="regimen/drugs/drug/activeIngredients|regimen/drugs/drug/ageDosing|regimen/drugs/drug/wGroupDosing|regimen/drugs/drug/weightDosing"/>

<!-- Not processing molecular or invitro (yet....) -->
<xsl:template match="molecular|invitro"/>

<!-- Insert again where required -->
<xsl:template match="regimen/drugs/drug">
<xsl:copy>
<xsl:apply-templates/>
<xsl:copy-of select="@*|*[count(node()/node()) > 0]"/>
</xsl:copy>
</xsl:template>


</xsl:stylesheet>