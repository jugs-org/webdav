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
 * Unit test for {@link Set}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class SetTest {
	@DataPoints
	public static final Object[][] DATA_POINTS = { { new Set(new Prop()), "<D:set xmlns:D=\"DAV:\"><D:prop/></D:set>", new Prop() } };

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
		final Set actual = JAXB.unmarshal(reader, Set.class);
		final Set expected = (Set) dataPoint[0];
		assertThat(actual, is(expected));
		assertThat(actual.getProp(), is(dataPoint[2]));
	}
}
