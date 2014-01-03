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

package net.java.dev.webdav.jaxrs.xml.elements;

import static java.net.URI.create;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link HRef}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class HRefTest extends AbstractJaxbCoreFunctionality<HRef> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullURI() {
		new HRef((URI) null);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullString() {
		new HRef((String) null);
	}

	private static String STRING_VALUE = "http://localhost";

	private static URI URI_VALUE = create(STRING_VALUE);

	@DataPoint
	public static final Object[] CASE_URI_CONSTRUCTOR = { new HRef(STRING_VALUE), "<D:href xmlns:D=\"DAV:\">http://localhost</D:href>", URI_VALUE, STRING_VALUE };

	@DataPoint
	public static final Object[] CASE_STRING_CONSTRUCTOR = { new HRef(URI_VALUE), "<D:href xmlns:D=\"DAV:\">http://localhost</D:href>", URI_VALUE, STRING_VALUE };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final HRef actual, final HRef expected, final Object[] dataPoint) {
		try {
			assertThat(actual.getURI(), is(dataPoint[2]));
			assertThat(actual.getValue(), is(dataPoint[3]));
			assertThat(expected.getURI(), is(dataPoint[2]));
			assertThat(expected.getValue(), is(dataPoint[3]));
		} catch (final URISyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Override
	protected final HRef getInstance() {
		return new HRef(URI_VALUE);
	}

	@Override
	protected final String getString() {
		return "HRef[http://localhost]";
	}
}
