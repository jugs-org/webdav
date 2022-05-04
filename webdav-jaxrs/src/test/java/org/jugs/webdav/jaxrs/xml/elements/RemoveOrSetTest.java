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
import org.jugs.webdav.jaxrs.NullArgumentException;

import org.jugs.webdav.jaxrs.xml.elements.Prop;
import org.jugs.webdav.jaxrs.xml.elements.RemoveOrSet;
import org.junit.Test;

/**
 * Unit test for {@link RemoveOrSet}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class RemoveOrSetTest {
	private static final class RemoveOrSetSubclass extends RemoveOrSet {/* Extended to be able to test abstract class */
		public RemoveOrSetSubclass(final Prop prop) {
			super(prop);
		}
	}

	@Test
	public final void constructAndGet() {
		final RemoveOrSet removeOrSet = new RemoveOrSetSubclass(new Prop());
		assertThat(removeOrSet.getProp(), is(new Prop()));
	}

	@Test(expected = NullArgumentException.class)
	public final void nullArgumentException() throws NullArgumentException {
		new RemoveOrSetSubclass(null);
	}
}
