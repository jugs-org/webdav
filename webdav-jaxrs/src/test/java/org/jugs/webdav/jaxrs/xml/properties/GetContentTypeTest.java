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
 * Unit test for {@link GetContentType}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetContentTypeTest extends AbstractJaxbCoreFunctionality<GetContentType> {

	@Test
	void constructorDoesNotAcceptNullAsName() {
		assertThrows(NullArgumentException.class, () -> new GetContentType(null));
	}

	private static final Object[] GETCONTENTTYPE = { GetContentType.GETCONTENTTYPE, "<D:getcontenttype xmlns:D=\"DAV:\"/>", "" };
	private static final Object[] MEDIATYPE_CONSTRUCTOR = { new GetContentType("SomeMediaType"),
			"<D:getcontenttype xmlns:D=\"DAV:\">SomeMediaType</D:getcontenttype>", "SomeMediaType" };

	@Test
	void marshallingGetcontenttype() throws JAXBException {
		marshalling(GETCONTENTTYPE);
	}

	@Test
	void unmarshallingGetcontenttype() throws JAXBException {
		unmarshalling(GETCONTENTTYPE);
	}

	@Test
	void marshallingMediatypeConstructor() throws JAXBException {
		marshalling(MEDIATYPE_CONSTRUCTOR);
	}

	@Test
	void unmarshallingMediatypeConstructor() throws JAXBException {
		unmarshalling(MEDIATYPE_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final GetContentType actual, final GetContentType expected, final Object[] dataPoint) {
		assertThat(actual.getMediaType(), is(dataPoint[2]));
		assertThat(expected.getMediaType(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public GetContentType getcontenttype;
	}

	@Test
	void shouldUnmarshalGETCONTENTTYPEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:getcontenttype/>";
		// when
		final GetContentType unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).getcontenttype;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(GetContentType.GETCONTENTTYPE)));
	}

	@Override
	protected GetContentType getInstance() {
		return new GetContentType("SomeMediaType");
	}

	@Override
	protected String getString() {
		return "GetContentType[SomeMediaType]";
	}

}
