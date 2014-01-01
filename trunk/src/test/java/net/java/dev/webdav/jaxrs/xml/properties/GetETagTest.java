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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link GetETag}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetETagTest extends AbstractJaxbCoreFunctionality<GetETag> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsName() {
		new GetETag(null);
	}

	@DataPoint
	public static final Object[] SINGLETON = { GetETag.GETETAG, "<D:getetag xmlns:D=\"DAV:\"/>", "" };

	@DataPoint
	public static final Object[] ETAG_CONSTRUCTOR = { new GetETag("SomeETag"), "<D:getetag xmlns:D=\"DAV:\">SomeETag</D:getetag>", "SomeETag" };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final GetETag actual, final GetETag expected, final Object[] dataPoint) {
		assertThat(actual.getEntityTag(), is(dataPoint[2]));
		assertThat(expected.getEntityTag(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public GetETag getetag;
	}

	@Test
	public final void shouldUnmarshalGetETagConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:getetag/>";

		// when
		final GetETag unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).getetag;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(GetETag.GETETAG)));
	}
}
