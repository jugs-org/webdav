/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs.xml.elements;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link TimeOut}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class TimeOutTest extends AbstractJaxbCoreFunctionality<TimeOut> {
	@DataPoint
	public static final Object[] INFINITE = { TimeOut.INFINITE, "<D:timeout xmlns:D=\"DAV:\">Infinite</D:timeout>", MAX_VALUE, TRUE };

	@DataPoint
	public static final Object[] SECOND = { new TimeOut(60), "<D:timeout xmlns:D=\"DAV:\">Second-60</D:timeout>", 60L, FALSE };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final TimeOut actual, final TimeOut expected, final Object[] dataPoint) {
		assertThat(actual.getSeconds(), is(dataPoint[2]));
		assertThat(actual.isInfinite(), is(dataPoint[3]));
		assertThat(expected.getSeconds(), is(dataPoint[2]));
		assertThat(expected.isInfinite(), is(dataPoint[3]));
	}

	/**
	 * JAXB Workaround: JAXB only invokes {@link XmlAdapter} if element is wrapped.
	 */
	@XmlRootElement
	private static final class X {
		public TimeOut timeout;
	}

	@Test
	public final void shouldUnmarshalINFINITEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:timeout>Infinite</D:timeout>";

		// when
		final TimeOut unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).timeout;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(TimeOut.INFINITE)));
	}
}
