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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.NullArgumentException;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

/**
 * Unit test for {@link Set}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class SetTest extends AbstractJaxbCoreFunctionality<Set> {
	private static final Prop PROP = new Prop();

	@DataPoint
	public static final Object[] DATA_POINT = { new Set(PROP), "<D:set xmlns:D=\"DAV:\"><D:prop/></D:set>", PROP };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Set actual, final Set expected, final Object[] dataPoint) {
		assertThat(actual.getProp(), is(dataPoint[2]));
		assertThat(expected.getProp(), is(dataPoint[2]));
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNull() {
		new Set(null);
	}

	@Override
	protected final Set getInstance() {
		return new Set(PROP);
	}

	@Override
	protected final String getString() {
		return "Set[Prop[[]]]";
	}
}
