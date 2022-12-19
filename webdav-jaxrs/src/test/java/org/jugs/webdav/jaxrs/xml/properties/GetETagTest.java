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
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link GetETag}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetETagTest extends AbstractJaxbCoreFunctionality<GetETag> {

	@Test
	void constructorDoesNotAcceptNullAsName() {
		assertThrows(NullArgumentException.class, () -> new GetETag(null));
	}

	private static final Object[] GETETAG = { GetETag.GETETAG, "<D:getetag xmlns:D=\"DAV:\"/>", "" };
	private static final Object[] ETAG_CONSTRUCTOR = { new GetETag("SomeETag"), "<D:getetag xmlns:D=\"DAV:\">SomeETag</D:getetag>", "SomeETag" };

	@Test
	void marshallingGetetag() throws JAXBException {
		marshalling(GETETAG);
	}

	@Test
	void unmarshallingGetetag() throws JAXBException {
		unmarshalling(GETETAG);
	}

	@Test
	void marshallingEtagConstructor() throws JAXBException {
		marshalling(ETAG_CONSTRUCTOR);
	}

	@Test
	void unmarshallingEtagConstructor() throws JAXBException {
		unmarshalling(ETAG_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final GetETag actual, final GetETag expected, final Object[] dataPoint) {
		assertThat(actual.getEntityTag(), is(dataPoint[2]));
		assertThat(expected.getEntityTag(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public GetETag getetag;
	}

	@Test
	void shouldUnmarshalGETETAGConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:getetag/>";
		// when
		final GetETag unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).getetag;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(GetETag.GETETAG)));
	}

	@Override
	protected GetETag getInstance() {
		return new GetETag("ETAG");
	}

	@Override
	protected String getString() {
		return "GetETag[ETAG]";
	}

}
