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

package net.java.dev.webdav.jaxrs.xml.properties;

import static net.java.dev.webdav.jaxrs.ImmutableDate.immutable;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.NullArgumentException;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link CreationDate}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class CreationDateTest {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNull() {
		new CreationDate(null);
	}

	@DataPoints
	public static final Object[][] DATA_POINTS = new Object[][] {
		{ new CreationDate(), "<D:creationdate xmlns:D=\"DAV:\"/>" },
		{ new CreationDate(new Date(1)), "<D:creationdate xmlns:D=\"DAV:\">1970-01-01T00:00:00.001Z</D:creationdate>" },
	};

	@Theory
	public final void marshalling(final Object[] dataPoint) {
		final Writer writer = new StringWriter();
		JAXB.marshal(dataPoint[0], writer);
		final String actualXml = writer.toString();
		final String expectedXml = (String) dataPoint[1];
		assertThat(the(actualXml), isEquivalentTo(the(expectedXml)));
	}

	@Theory
	public final void unmarshalling(final Object[] dataPoint) {
		final CreationDate actualElement = JAXB.unmarshal(new StringReader((String) dataPoint[1]), CreationDate.class);
		final CreationDate expectedElement = (CreationDate) dataPoint[0];
		assertThat(actualElement, is(equalTo(expectedElement)));
	}

	@Theory
	public final void dateIsEffectivelyImmutable(final Object[] dataPoint) {
		assertEffectivelyImmutableDate((CreationDate) dataPoint[0]);
		assertEffectivelyImmutableDate(JAXB.unmarshal(new StringReader((String) dataPoint[1]), CreationDate.class));
	}

	private static final void assertEffectivelyImmutableDate(final CreationDate immutableObject) {
		final Date resultOfFirstCall = immutableObject.getDateTime();
		final Date resultOfSecondCall = immutableObject.getDateTime();
		assertThat(resultOfFirstCall, is(anyOf(immutable(), not(sameInstance(resultOfSecondCall)), nullValue())));
	}
}
