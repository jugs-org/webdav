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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Remove}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class RemoveTest extends AbstractJaxbCoreFunctionality<Remove> {
	private static final Prop PROP = new Prop();

	@DataPoint
	public static final Object[] DATA_POINT = { new Remove(PROP), "<D:remove xmlns:D=\"DAV:\"><D:prop/></D:remove>", PROP };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final Remove actual, final Remove expected, final Object[] dataPoint) {
		assertThat(actual.getProp(), is(dataPoint[2]));
		assertThat(expected.getProp(), is(dataPoint[2]));
	}

	@Test
	public final void constructorDoesNotAcceptNull() {
		assertThrows(NullArgumentException.class, () -> new Remove(null));
	}

	@Override
	protected final Remove getInstance() {
		return new Remove(PROP);
	}

	@Override
	protected final String getString() {
		return "Remove[Prop[[]]]";
	}
}
