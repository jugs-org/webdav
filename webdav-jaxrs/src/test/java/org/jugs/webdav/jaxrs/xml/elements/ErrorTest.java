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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLanguage;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Error}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ErrorTest extends AbstractJaxbCoreFunctionality<Error> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullError() {
		new Error(null);
	}

	private static final Object FIRST_ERROR = new Prop();

	private static final Object SECOND_ERROR = GetContentLanguage.GETCONTENTLANGUAGE;

	@DataPoint
	public static final Object[] ONE_ERROR = { new Error(FIRST_ERROR), "<D:error xmlns:D=\"DAV:\"><D:prop/></D:error>", asList(FIRST_ERROR) };

	@DataPoint
	public static final Object[] TWO_ERRORS = { new Error(FIRST_ERROR, SECOND_ERROR), "<D:error xmlns:D=\"DAV:\"><D:prop/><D:getcontentlanguage/></D:error>",
			asList(FIRST_ERROR, SECOND_ERROR) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Error actual, final Error expected, final Object[] dataPoint) {
		assertThat(actual.getErrors(), is(dataPoint[2]));
		assertThat(expected.getErrors(), is(dataPoint[2]));
	}

	@Override
	protected final Error getInstance() {
		return new Error("ERROR");
	}

	@Override
	protected final String getString() {
		return "Error[[ERROR]]";
	}
}
