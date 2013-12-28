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

package net.java.dev.webdav.jaxrs.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
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
 * for getters, which will get checked using {@link #assertThatGettersProvideExpectedValues(Object, Object, Object[])}.
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
		context = WebDavJAXBContextBuilder.build();
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

		final T singleton = this.getSingleton();
		if (singleton != null)
			this.runSingletonTests(actual, singleton);
	}

	private final void runSingletonTests(final T actual, final T singleton) {
		this.shouldUnmarshalToSingletonInstance(actual, singleton);
		this.shouldReturnSingletonHashCodeForAnyInstance(actual, singleton);
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

	/**
	 * Invoked by {@link #unmarshalling(Object[])} to allow descendant to check whether unmarshalled instance actually is a singleton (if this is intended by
	 * the particular class / test).
	 * 
	 * @param actual
	 *            The unmarshalled Java object instance.
	 * @param expected
	 *            The singleton instance.
	 */
	private final void shouldUnmarshalToSingletonInstance(final T actual, final T expected) {
		// given
		final T singletonInstance = expected;
		final T unmarshalledInstance = actual;

		// when
		// (doing nothing, as unmarshalling already happened)

		// then
		assertThat(unmarshalledInstance, sameInstance(singletonInstance));
	}

	/**
	 * Invoked by {@link #unmarshalling(Object[])} to allow descendant to check whether unmarshalled instance actually is using one singleton hash code (if this
	 * is intended by the particular class / test).
	 * 
	 * @param actual
	 *            The unmarshalled Java object instance.
	 * @param expected
	 *            The singleton instance.
	 */
	private final void shouldReturnSingletonHashCodeForAnyInstance(final T actual, final T expected) {
		// given
		final T singletonInstance = expected;
		final T unmarshalledInstance = actual;

		// when
		final int unmarshalledHashCode = unmarshalledInstance.hashCode();
		final int singletonHashCode = singletonInstance.hashCode();

		// then
		assertThat(unmarshalledHashCode, is(singletonHashCode));
	}

	/**
	 * Invoked by {@link #unmarshalling(Object[])} to allow descendant to decide whether this test shall check whether unmarshalled instance actually is a
	 * singleton. To activate this test, the descendant returns the expected singleton instance. This default implementation returns {@code null} , hence the
	 * test is skipped.
	 * 
	 * @return Singleton instance expected as unmarshalling result, or {@code null} to skip this test.
	 */
	protected T getSingleton() {
		return null;
	}
}
