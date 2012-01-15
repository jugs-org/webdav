package net.java.dev.webdav.jaxrs.xml.elements;

import static net.java.dev.webdav.jaxrs.xml.elements.Depth.INFINITY;
import static net.java.dev.webdav.jaxrs.xml.elements.Depth.ONE;
import static net.java.dev.webdav.jaxrs.xml.elements.Depth.ZERO;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.xml.elements.Depth;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public final class DepthTest {
	@DataPoints
	public static final Object[][] DATA_POINTS = {
		{ ZERO, "<depth xmlns=\"DAV:\">0</depth>" },
		{ ONE, "<depth xmlns=\"DAV:\">1</depth>" },
		{ INFINITY, "<depth xmlns=\"DAV:\">infinity</depth>" }
	};

	@Theory
	public final void marshalling(final Object[] dataPoint) {
		final Writer writer = new StringWriter();
		JAXB.marshal(dataPoint[0], writer);
		final String actual = writer.toString();
		final String expected = (String) dataPoint[1];
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@Theory
	public final void unmarshalling(final Object[] dataPoint) {
		final Reader reader = new StringReader((String) dataPoint[1]);
		final Depth actual = JAXB.unmarshal(reader, Depth.class);
		final Depth expected = (Depth) dataPoint[0];
		assertThat(actual, is(expected));
	}
}
