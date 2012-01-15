package net.java.dev.webdav.jaxrs.xml.conditions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import org.junit.Test;

public final class PropFindFiniteDepthTest {
	@Test
	public final void marshalling() {
		final Writer writer = new StringWriter();
		JAXB.marshal(new PropFindFiniteDepth(), writer);
		final String actual = writer.toString();
		final String expected = "<D:propfind-finite-depth xmlns:D=\"DAV:\"/>";
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Test
	public final void unmarshalling() {
		final Reader reader = new StringReader("<D:propfind-finite-depth xmlns:D=\"DAV:\"/>");
		final PropFindFiniteDepth actual = JAXB.unmarshal(reader, PropFindFiniteDepth.class);
		final PropFindFiniteDepth expected = new PropFindFiniteDepth();
		assertThat(actual, is(equalTo(expected)));
	}
}
