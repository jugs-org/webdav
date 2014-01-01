/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package net.java.dev.webdav.jaxrs.xml.properties;

import static java.lang.String.format;
import static net.java.dev.webdav.util.DateBuilder.date;
import static net.java.dev.webdav.util.UnitTestUtilities.assertEffectivelyImmutableDate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theory;

/**
 * Unit test for {@link CreationDate}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class CreationDateTest extends AbstractJaxbCoreFunctionality<CreationDate> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNull() {
		new CreationDate(null);
	}

	@DataPoint
	public static final Object[] CREATIONDATE = new Object[] { CreationDate.CREATIONDATE, "<D:creationdate xmlns:D=\"DAV:\"/>", null };

	@DataPoint
	public static final Object[] DATE_CONSTRUCTOR = new Object[] { new CreationDate(date(2012, 11, 12, 13, 14, 15, 16, "UTC")),
			"<D:creationdate xmlns:D=\"DAV:\">2012-11-12T13:14:15.016Z</D:creationdate>", date(2012, 11, 12, 13, 14, 15, 16, "UTC") };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final CreationDate actual, final CreationDate expected, final Object[] dataPoint) {
		assertThat(actual.getDateTime(), is(dataPoint[2]));
		assertThat(expected.getDateTime(), is(dataPoint[2]));
	}

	@Theory
	public final void dateIsEffectivelyImmutable(final Object[] dataPoint) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		assertEffectivelyImmutableDate(dataPoint[0], "getDateTime");
		assertEffectivelyImmutableDate(JAXB.unmarshal(new StringReader((String) dataPoint[1]), CreationDate.class), "getDateTime");
	}

	@XmlRootElement
	public static class X {
		public CreationDate creationdate;
	}

	@Test
	public final void shouldUnmarshalCREATIONDATEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:creationdate/>";

		// when
		final CreationDate unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).creationdate;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(CreationDate.CREATIONDATE)));
	}
}
