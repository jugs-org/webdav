package net.java.dev.webdav.jaxrs.elements;

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

import net.java.dev.webdav.jaxrs.xml.elements.Collection;

import org.junit.Test;

public final class CollectionTest {
	@Test
	public final void marshalling() {
		final Writer writer = new StringWriter();
		JAXB.marshal(new Collection(), writer);
		final String actual = writer.toString();
		final String expected = "<D:collection xmlns:D=\"DAV:\"/>";
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Test
	public final void unmarshalling() {
		final Reader reader = new StringReader("<D:collection xmlns:D=\"DAV:\"/>");
		final Collection actual = JAXB.unmarshal(reader, Collection.class);
		final Collection expected = new Collection();
		assertThat(actual, is(equalTo(expected)));
	}
}
