/*
 * Copyright 2013 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Abstract unit test for elements.
 * 
 * Tests marshalling and unmarshalling of descendant classes. Descendant has to provide {@code @DataPoint}(s) of array type {@code Object[]}, where element 0
 * contains the Java representation of the object to test, and element 1 contains the XML representation. Further elements can be used to provide expectations
 * for getters, which will get checked using {@link #assertThatGettersProvideExpectedValues(Object, Object[])}.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public abstract class AbstractJaxbCoreFunctionality<T> {

	public static JAXBContext context;

	public static Marshaller marshaller;

	public static Unmarshaller unmarshaller;

	@BeforeClass
	public static final void setUpContext() throws JAXBException {
		context = new WebDavJAXBContextBuilder().build();
		marshaller = context.createMarshaller();
		unmarshaller = context.createUnmarshaller();
	}

	@SuppressWarnings("unchecked")
	@Theory
	public final void marshalling(final Object[] dataPoint) throws JAXBException {
		final T unmarshalledRepresentation = (T) dataPoint[0];
		final Writer writer = new StringWriter();
		marshaller.marshal(unmarshalledRepresentation, writer);
		final String actual = writer.toString();
		final String expected = (String) dataPoint[1];
		assertThat(the(actual), isEquivalentTo(the(expected)));
	}

	@SuppressWarnings("unchecked")
	@Theory
	public final void unmarshalling(final Object[] dataPoint) throws JAXBException {
		final T expected = (T) dataPoint[0];
		final String marshalledRepresentation = (String) dataPoint[1];
		final Reader reader = new StringReader(marshalledRepresentation);
		final T actual = (T) unmarshaller.unmarshal(reader);
		assertThat(actual, is(expected));
		this.assertThatGettersProvideExpectedValues(actual, expected, dataPoint);
	}

	/**
	 * Invoked by {@link #unmarshalling(Object[])} to allow descendant to check getters after unmarshalling.
	 * 
	 * @param actual
	 *            The actually unmarshalled Java object instance.
	 * @param expected
	 *            The expected Java object instance.
	 * @param dataPoint
	 *            The current data point used to run this test. Descendant can use this to get expectation values for getters.
	 */
	protected void assertThatGettersProvideExpectedValues(final T actual, final T expected, final Object[] dataPoint) {
		// Does nothing by default.
	}
}
