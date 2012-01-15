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

public final class LockTokenMatchesRequestUriTest {
	@Test
	public final void marshalling() {
		final Writer writer = new StringWriter();
		JAXB.marshal(new LockTokenMatchesRequestUri(), writer);
		final String actual = writer.toString();
		final String expected = "<D:lock-token-matches-request-uri xmlns:D=\"DAV:\"/>";
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Test
	public final void unmarshalling() {
		final Reader reader = new StringReader("<D:lock-token-matches-request-uri xmlns:D=\"DAV:\"/>");
		final LockTokenMatchesRequestUri actual = JAXB.unmarshal(reader, LockTokenMatchesRequestUri.class);
		final LockTokenMatchesRequestUri expected = new LockTokenMatchesRequestUri();
		assertThat(actual, is(equalTo(expected)));
	}
}
