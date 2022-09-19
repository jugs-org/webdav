/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.properties;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.util.DateBuilder;
import org.jugs.webdav.util.UnitTestUtilities;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link GetLastModified}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetLastModifiedTest extends AbstractJaxbCoreFunctionality<GetLastModified> {

	@Test
	void constructorDoesNotAcceptNull() {
		assertThrows(NullArgumentException.class, () -> new GetLastModified(null));
	}

	private static final Object[] GETLASTMODIFIED = new Object[] { GetLastModified.GETLASTMODIFIED, "<D:getlastmodified xmlns:D=\"DAV:\"/>", null };
	private static final Object[] DATE_CONSTRUCTOR = new Object[] {new GetLastModified(
            DateBuilder.date(2012, 11, 12, 13, 14, 15, 0, "GMT")),
                                                                  "<D:getlastmodified xmlns:D=\"DAV:\">Mon, 12 Nov 2012 13:14:15 GMT</D:getlastmodified>", DateBuilder.date(2012, 11, 12, 13, 14, 15, 0, "GMT") };

	@Test
	void marshallingGetlastmodified() throws JAXBException {
		marshalling(GETLASTMODIFIED);
	}

	@Test
	void unmarshallingGetlastmodified() throws JAXBException {
		unmarshalling(GETLASTMODIFIED);
	}

	@Test
	void marshallingDateConstructor() throws JAXBException {
		marshalling(DATE_CONSTRUCTOR);
	}

	@Test
	void unmarshallingDateConstructor() throws JAXBException {
		unmarshalling(DATE_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final GetLastModified actual, final GetLastModified expected, final Object[] dataPoint) {
		assertThat(actual.getDateTime(), is(dataPoint[2]));
		assertThat(expected.getDateTime(), is(dataPoint[2]));
	}

	@Test
	void dateIsEffectivelyGetlastmodified() throws ReflectiveOperationException, SecurityException {
		dateIsEffectivelyImmutable(GETLASTMODIFIED);
	}

	@Test
	void dateIsEffectivelyImmutableDateConstructor() throws ReflectiveOperationException, SecurityException {
		dateIsEffectivelyImmutable(DATE_CONSTRUCTOR);
	}

	private static void dateIsEffectivelyImmutable(final Object[] dataPoint) throws ReflectiveOperationException, SecurityException {
		UnitTestUtilities.assertEffectivelyImmutableDate(dataPoint[0], "getDateTime");
		UnitTestUtilities.assertEffectivelyImmutableDate(JAXB.unmarshal(new StringReader((String) dataPoint[1]), GetLastModified.class), "getDateTime");
	}

	@XmlRootElement
	public static class X {
		public GetLastModified getlastmodified;
	}

	@Test
	void shouldUnmarshalGETLASTMODIFIEDConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:getlastmodified/>";
		// when
		final GetLastModified unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).getlastmodified;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(GetLastModified.GETLASTMODIFIED)));
	}

	@Override
	protected GetLastModified getInstance() {
		return new GetLastModified(DateBuilder.date(2000, 1, 1, 0, 0, 0, 0, "UTC"));
	}

	@Override
	protected String getString() {
		return "GetLastModified[Sat Jan 01 01:00:00 CET 2000]";
	}

}
