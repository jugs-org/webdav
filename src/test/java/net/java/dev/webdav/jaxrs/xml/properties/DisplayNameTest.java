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
 * Unit test for {@link DisplayName}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DisplayNameTest extends AbstractJaxbCoreFunctionality<DisplayName> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsName() {
		new DisplayName(null);
	}

	@DataPoint
	public static final Object[] DISPLAYNAME = { DisplayName.DISPLAYNAME, "<D:displayname xmlns:D=\"DAV:\"/>", "" };

	@DataPoint
	public static final Object[] NAME_CONSTRUCTOR = { new DisplayName("SomeName"), "<D:displayname xmlns:D=\"DAV:\">SomeName</D:displayname>", "SomeName" };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final DisplayName actual, final DisplayName expected, final Object[] dataPoint) {
		assertThat(actual.getName(), is(dataPoint[2]));
		assertThat(expected.getName(), is(dataPoint[2]));
	}

	@XmlRootElement
	public static class X {
		public DisplayName displayname;
	}

	@Test
	public final void shouldUnmarshalDISPLAYNAMEConstant() throws JAXBException {
		// given
		final String marshalledForm = "<D:displayname/>";

		// when
		final DisplayName unmarshalledInstance = ((X) JAXBContext.newInstance(X.class).createUnmarshaller()
				.unmarshal(new StringReader(format("<D:x xmlns:D=\"DAV:\">%s</D:x>", marshalledForm)))).displayname;

		// then
		assertThat(unmarshalledInstance, is(sameInstance(DisplayName.DISPLAYNAME)));
	}

	@Override
	protected final DisplayName getInstance() {
		return new DisplayName("SomeName");
	}
}
