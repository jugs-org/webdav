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

package net.java.dev.webdav.util;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit test for {@link Utilities}
 * 
 * @author Markus KARG (mkarg@junit.org)
 */
public final class UtilitiesTest {
	@Test
	public final void sameOrEqual() {
		assertThat(Utilities.sameOrEqual(null, null), is(true));
		assertThat(Utilities.sameOrEqual("A", "A"), is(true));
		assertThat(Utilities.sameOrEqual("A", "B"), is(false));
		assertThat(Utilities.sameOrEqual("A", null), is(false));
		assertThat(Utilities.sameOrEqual(null, "B"), is(false));
	}

	private static final class A {
		// Intentionally left blank.
	}

	private static final class PrivatelyConstructed {
		private PrivatelyConstructed() {
			// Intentionally left blank.
		}
	}

	private static enum Enum {
		SOME_VALUE
	}

	private static enum EmptyEnum {
		// Intentionally left blank.
	}

	@Test
	public final void buildInstanceOf() {
		assertThat(Utilities.buildInstanceOf(null), is(nullValue()));
		assertThat(Utilities.buildInstanceOf(A.class), is(instanceOf(A.class)));
		assertThat(Utilities.buildInstanceOf(PrivatelyConstructed.class), is(instanceOf(PrivatelyConstructed.class)));
		assertThat(Utilities.buildInstanceOf(Enum.class), is(instanceOf(Enum.class)));
		assertThat(Utilities.buildInstanceOf(EmptyEnum.class), is(nullValue()));
	}

	@Test
	public final void append() {
		final Integer[] firstArray = new Integer[] { 1, 2, 3 };
		final Integer[] secondArray = new Integer[] { 4, 5, 6 };
		assertThat(Utilities.append(firstArray, secondArray), equalTo(new Integer[] { 1, 2, 3, 4, 5, 6 }));
	}
}
