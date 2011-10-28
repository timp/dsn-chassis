<?xml version="1.0"?>

<!DOCTYPE xsl:stylesheet [ 
    <!ENTITY nbsp "&#160;"> 
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" media-type="text/html"/>

<xsl:template match="/">
<NewQuery>
    <new>worldwide test</new>
    <Detail>
       <NewCriteria>1. <xsl:value-of select="//Criteria" /></NewCriteria>
       <NewAttribute>2. <xsl:value-of select="//Attribute"/></NewAttribute>
       <NewValue>3. <xsl:value-of select="//Value"/></NewValue>
    </Detail>
</NewQuery>        
</xsl:template>

</xsl:stylesheet>