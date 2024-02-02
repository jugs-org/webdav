/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2024 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.io.StringReader;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Long.MAX_VALUE;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link TimeOut}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class TimeOutTest extends AbstractJaxbCoreFunctionality<TimeOut> {

	private static final Object[] INFINITE = { TimeOut.INFINITE, "<D:timeout xmlns:D=\"DAV:\">Infinite</D:timeout>", MAX_VALUE, TRUE };

	private static final Object[] SECOND = { new TimeOut(60L), "<D:timeout xmlns:D=\"DAV:\">Second-60</D:timeout>", 60L, FALSE };

	@Test
	void marshallingInfinite() throws JAXBException {
		marshalling(INFINITE);
	}

	@Test
	void unmarshallingInfinite() throws JAXBException {
		unmarshalling(INFINITE);
	}

	@Test
	void marshallingSecond() throws JAXBException {
		marshalling(SECOND);
	}

	@Test
	void unmarshallingSecond() throws JAXBException {
		unmarshalling(SECOND);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final TimeOut actual, final TimeOut expected, final Object[] dataPoint) {
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
	void shouldUnmarshalINFINITEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:timeout>Infinite</D:timeout>";
		// when
		final TimeOut unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).timeout;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(TimeOut.INFINITE)));
	}

	@Override
	protected TimeOut getInstance() {
		return new TimeOut(90L);
	}

	@Override
	protected String getString() {
		return "TimeOut[90]";
	}

	@Test
	void shouldParseINFINITEConstant() {
		// given
		final String timeType = "Infinite";
		// when
		final TimeOut timeOut = TimeOut.valueOf(timeType);
		// then
		assertThat(timeOut, is(sameInstance(TimeOut.INFINITE)));
	}

	@Test
	void shouldParseSeconds() {
		// given
		final String timeType = "Second-1234567890";
		// when
		final TimeOut timeOut = TimeOut.valueOf(timeType);
		// then
		assertThat(timeOut.getSeconds(), is(1234567890L));
	}

}
