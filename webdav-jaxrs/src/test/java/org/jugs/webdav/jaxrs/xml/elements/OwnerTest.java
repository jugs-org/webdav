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
import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

/**
 * Unit test for {@link Owner}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class OwnerTest extends AbstractJaxbCoreFunctionality<Owner> {
	private static String ANY = "ANY";

	@DataPoints
	public static final Object[][] DATA_POINTS = { { new Owner(), "<D:owner xmlns:D=\"DAV:\"/>", EMPTY_LIST },
			{ new Owner(ANY), "<D:owner xmlns:D=\"DAV:\">ANY</D:owner>", asList(ANY) } };

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsLockEntries() {
		new Owner((Object[]) null);
	}

	@Override
	protected void assertThatGettersProvideExpectedValues(final Owner actual, final Owner expected, final Object[] dataPoint) {
		assertThat(actual.getAny(), is(dataPoint[2]));
		assertThat(expected.getAny(), is(dataPoint[2]));
	}

	@Override
	protected final Owner getInstance() {
		return new Owner(ANY);
	}

	@Override
	protected final String getString() {
		return "Owner[[ANY]]";
	}
}
