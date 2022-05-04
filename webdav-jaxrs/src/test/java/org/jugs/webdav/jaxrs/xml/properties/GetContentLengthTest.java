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

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link GetContentLength}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetContentLengthTest extends AbstractJaxbCoreFunctionality<GetContentLength> {
	@DataPoint
	public static final Object[] GETCONTENTLENGTH = { GetContentLength.GETCONTENTLENGTH, "<D:getcontentlength xmlns:D=\"DAV:\"/>", 0L };

	@DataPoint
	public static final Object[] LENGTH_CONSTRUCTOR = { new GetContentLength(123L), "<D:getcontentlength xmlns:D=\"DAV:\">123</D:getcontentlength>", 123L };

	@SuppressWarnings("deprecation")
	// 'getLanguageTag' is still supported!
	@Override
	protected final void assertThatGettersProvideExpectedValues(final GetContentLength actual, final GetContentLength expected, final Object[] dataPoint) {
		assertThat(actual.getContentLength(), is(dataPoint[2]));
		assertThat(expected.getContentLength(), is(dataPoint[2]));
		assertThat(actual.getLanguageTag(), is(dataPoint[2]));
		assertThat(expected.getLanguageTag(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public GetContentLength getcontentlength;
	}

	@Test
	public final void shouldUnmarshalGETCONTENTLENGTHConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:getcontentlength/>";

		// when
		final GetContentLength unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).getcontentlength;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(GetContentLength.GETCONTENTLENGTH)));
	}

	@Override
	protected final GetContentLength getInstance() {
		return new GetContentLength(123L);
	}

	@Override
	protected final String getString() {
		return "GetContentLength[123]";
	}
}
