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

package net.java.dev.webdav.jaxrs.xml.elements;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import net.java.dev.webdav.jaxrs.NullArgumentException;

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
