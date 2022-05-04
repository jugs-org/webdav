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

package org.jugs.webdav.util;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.jugs.webdav.jaxrs.NullArgumentException;

import org.jugs.webdav.util.Utilities;
import org.junit.Test;

/**
 * Unit test for {@link Utilities}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class UtilitiesTest {
	private static final class A {
		// Intentionally left blank.
	}

	private static final class PrivatelyConstructed {
		private PrivatelyConstructed() {
			// Intentionally left blank.
		}
	}

	private static final class NoDefaultConstructor {
		private NoDefaultConstructor(final int ignore) {
			// Intentionally left blank.
		}
	}

	private static enum Enum {
		SOME_VALUE
	}

	private static enum EmptyEnum {
		// Intentionally left blank.
	}

	private static abstract class Abstract {
		// Intentionally left blank.
	}

	@Test
	public final void buildInstanceOf() {
		assertThat(Utilities.buildInstanceOf(null), is(nullValue()));
		assertThat(Utilities.buildInstanceOf(A.class), is(instanceOf(A.class)));
		assertThat(Utilities.buildInstanceOf(PrivatelyConstructed.class), is(instanceOf(PrivatelyConstructed.class)));
		assertThat(Utilities.buildInstanceOf(NoDefaultConstructor.class), is(nullValue()));
		assertThat(Utilities.buildInstanceOf(Enum.class), is(instanceOf(Enum.class)));
		assertThat(Utilities.buildInstanceOf(EmptyEnum.class), is(nullValue()));
		assertThat(Utilities.buildInstanceOf(Abstract.class), is(nullValue()));
	}

	@Test
	public final void shouldAppendArrayToArray() {
		// given
		final Integer[] firstArray = new Integer[] { 1, 2, 3 };
		final Integer[] secondArray = new Integer[] { 4, 5, 6 };

		// when
		final Integer[] result = Utilities.append(firstArray, secondArray);

		// then
		assertThat(result, equalTo(new Integer[] { 1, 2, 3, 4, 5, 6 }));
	}

	@Test
	public final void shouldAppendArrayToObject() {
		// given
		final Integer object = 1;
		final Integer[] array = new Integer[] { 2, 3, 4 };

		// when
		final List<Integer> result = Utilities.append(object, array);

		// then
		assertThat(result, equalTo(asList(1, 2, 3, 4)));
	}

	@Test(expected = NullArgumentException.class)
	public final void shouldPreventNullArgument() {
		Utilities.notNull(null, "name");
	}

	@Test
	public final void shouldBuildArrayFromVarArgs() {
		// given
		final String a = "A";
		final String b = "B";
		final String c = "C";

		// when
		final String[] array = Utilities.array(a, b, c);

		// then
		assertThat(array, is(new String[] { "A", "B", "C" }));
	}
}
