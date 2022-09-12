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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.junit.experimental.theories.DataPoint;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@link HRef}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class HRefTest extends AbstractJaxbCoreFunctionality<HRef> {

	@Test
	void constructorDoesNotAcceptNullURI() {
		assertThrows(NullArgumentException.class, () -> new HRef((URI) null));
	}

	@Test
	void constructorDoesNotAcceptNullString() {
		assertThrows(NullArgumentException.class, () -> new HRef((String) null));
	}

	private static final String STRING_VALUE = "http://localhost";

	private static final URI URI_VALUE = create(STRING_VALUE);

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
