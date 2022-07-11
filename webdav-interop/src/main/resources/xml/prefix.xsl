<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:dav="DAV:">
<xsl:param name="ns" select="'dav'"/> 
		<xsl:template match="*">
			<xsl:copy>
				<xsl:copy-of select="@*"/>
				<xsl:apply-templates/>
			</xsl:copy>
		</xsl:template>
		
		<xsl:template match="dav:multistatus">
			<dav:multistatus xmlns:dav="DAV:">
		      <xsl:apply-templates/>
		    </dav:multistatus> 
		</xsl:template>
		
		<xsl:template match="dav:*">
		    <xsl:element name="{concat($ns, ':', name())}">
		      <xsl:apply-templates/>
		    </xsl:element>
		</xsl:template>
</xsl:stylesheet>

