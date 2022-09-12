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
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.junit.experimental.theories.DataPoint;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Include}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class IncludeTest extends AbstractJaxbCoreFunctionality<Include> {
	@Test
	public final void constructorDoesNotAcceptNullURI() {
		assertThrows(NullArgumentException.class, () -> new Include((Object[]) null));
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

	@Override
	protected final Include getInstance() {
		return new Include(CREATION_DATE);
	}

	@Override
	protected final String getString() {
		return "Include[[CreationDate[null]]]";
	}
}
