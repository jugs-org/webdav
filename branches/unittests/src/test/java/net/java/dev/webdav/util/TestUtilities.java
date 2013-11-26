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

import static net.java.dev.webdav.jaxrs.ImmutableDate.immutable;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Utilities for unit tests.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class TestUtilities {

	/**
	 * Asserts that a method's {@link Date} getter in fact returns immutable objects, or a different object with every call.
	 * 
	 * @param immutableObject
	 *            The object to check.
	 * @param getter
	 *            The getter's name to check.
	 * @throws SecurityException
	 *             When the getter is not allowed to be called.
	 * @throws NoSuchMethodException
	 *             When no such getter exists.
	 * @throws InvocationTargetException
	 *             When the getter cannot get called.
	 * @throws IllegalArgumentException
	 *             When the getter needs arguments.
	 * @throws IllegalAccessException
	 *             When the getter is not public.
	 */
	public static final <T> void assertEffectivelyImmutableDate(final T immutableObject, final String getterName) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Method getter = immutableObject.getClass().getMethod(getterName);
		final Date resultOfFirstCall = (Date) getter.invoke(immutableObject);
		final Date resultOfSecondCall = (Date) getter.invoke(immutableObject);
		assertThat(resultOfFirstCall, is(anyOf(immutable(), not(sameInstance(resultOfSecondCall)), nullValue())));
	}
}
