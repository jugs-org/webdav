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

package net.java.dev.webdav.jaxrs.xml.elements;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class IncludeTest extends AbstractJaxbCoreFunctionality<Include> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullURI() {
		new Include((Object[]) null);
	}

	private static Object CREATION_DATE = new CreationDate();

	@DataPoint
	public static final Object[] SINGLE_VALUE_CONSTRUCTOR = { new Include(CREATION_DATE), "<D:include xmlns:D=\"DAV:\"><D:creationdate/></D:include>",
			asList(CREATION_DATE) };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Include actual, final Include expected, final Object[] dataPoint) {
		assertThat(actual.getIncludes(), is(dataPoint[2]));
		assertThat(expected.getIncludes(), is(dataPoint[2]));
	}
}
