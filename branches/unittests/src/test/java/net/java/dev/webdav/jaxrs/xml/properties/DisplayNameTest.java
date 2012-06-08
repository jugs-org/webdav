/*
 * Copyright 2012 Markus KARG
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

package net.java.dev.webdav.jaxrs.xml.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

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

	@DataPoints
	public static final Object[][] DATA_POINT = { { new DisplayName(), "<D:displayname xmlns:D=\"DAV:\"/>", "" },
			{ new DisplayName("SomeName"), "<D:displayname xmlns:D=\"DAV:\">SomeName</D:displayname>", "SomeName" } };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final DisplayName actual, final DisplayName expected, final Object[] dataPoint) {
		assertThat(actual.getName(), is(dataPoint[2]));
		assertThat(expected.getName(), is(dataPoint[2]));
	}
}
