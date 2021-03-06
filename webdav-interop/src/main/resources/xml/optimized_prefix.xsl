<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="xml" />
 <xsl:strip-space elements="*" />
 <xsl:template match="*[local-name() != name()]">
  <xsl:copy>
   <xsl:copy-of select="@*"/>
   <xsl:apply-templates/>
  </xsl:copy>
 </xsl:template>
 <xsl:template match="*[local-name() = name()]">
  <xsl:element namespace="{namespace-uri()}" name="{local-name()}">
   <xsl:copy-of select="@*"/>
   <xsl:apply-templates/>
  </xsl:element>
 </xsl:template>
</xsl:stylesheet>