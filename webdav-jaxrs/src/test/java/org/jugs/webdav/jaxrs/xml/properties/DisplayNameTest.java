/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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
 * Unit test for {@link DisplayName}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DisplayNameTest extends AbstractJaxbCoreFunctionality<DisplayName> {

	@Test
	void constructorDoesNotAcceptNullAsName() {
		assertThrows(NullArgumentException.class, () -> new DisplayName(null));
	}

	private static final Object[] DISPLAYNAME = { DisplayName.DISPLAYNAME, "<D:displayname xmlns:D=\"DAV:\"/>", "" };
	private static final Object[] NAME_CONSTRUCTOR = { new DisplayName("SomeName"), "<D:displayname xmlns:D=\"DAV:\">SomeName</D:displayname>", "SomeName" };

	@Test
	void marshallingDisplayname() throws JAXBException {
		marshalling(DISPLAYNAME);
	}

	@Test
	void unmarshallingDisplayname() throws JAXBException {
		unmarshalling(DISPLAYNAME);
	}

	@Test
	void marshallingNameConstructor() throws JAXBException {
		marshalling(NAME_CONSTRUCTOR);
	}

	@Test
	void unmarshallingNameConstructor() throws JAXBException {
		unmarshalling(NAME_CONSTRUCTOR);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final DisplayName actual, final DisplayName expected, final Object[] dataPoint) {
		assertThat(actual.getName(), is(dataPoint[2]));
		assertThat(expected.getName(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public DisplayName displayname;
	}

	@Test
	void shouldUnmarshalDISPLAYNAMEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:displayname/>";
		// when
		final DisplayName unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).displayname;
		// then
		assertThat(unmarshalledInstance, is(sameInstance(DisplayName.DISPLAYNAME)));
	}

	@Override
	protected DisplayName getInstance() {
		return new DisplayName("SomeName");
	}

	@Override
	protected String getString() {
		return "DisplayName[SomeName]";
	}

}
