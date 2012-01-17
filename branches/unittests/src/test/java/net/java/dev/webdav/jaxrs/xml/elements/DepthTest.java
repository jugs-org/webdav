/*
 * Copyright 2012 Markus KARG
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */

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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link Depth}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
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
