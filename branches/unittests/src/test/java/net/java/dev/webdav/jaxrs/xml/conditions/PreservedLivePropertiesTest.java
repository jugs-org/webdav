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

public final class PreservedLivePropertiesTest {
	@Test
	public final void marshalling() {
		final Writer writer = new StringWriter();
		JAXB.marshal(new PreservedLiveProperties(), writer);
		final String actual = writer.toString();
		final String expected = "<D:preserved-live-properties xmlns:D=\"DAV:\"/>";
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Test
	public final void unmarshalling() {
		final Reader reader = new StringReader("<D:preserved-live-properties xmlns:D=\"DAV:\"/>");
		final PreservedLiveProperties actual = JAXB.unmarshal(reader, PreservedLiveProperties.class);
		final PreservedLiveProperties expected = new PreservedLiveProperties();
		assertThat(actual, is(equalTo(expected)));
	}
}
