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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * Common purpose utilities.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Utilities {

	/**
	 * Prevents {@link NullPointerException} when checking two references for equality where the first reference can be {@code null}.
	 * 
	 * @param firstObject
	 *            If this is not {@code null}, its {@link #equals(Object)} method will get called.
	 * @param secondObject
	 *            This will be the sole parameter provided to the first reference's {@link #equals(Object)}.
	 * @return {@code true} if, and only if, either both references are null or referencing equal objects.
	 */
	public static final boolean sameOrEqual(final Object firstObject, final Object secondObject) {
		return firstObject == secondObject || firstObject != null && firstObject.equals(secondObject);
	}

	/**
	 * Supplies an instance of the specified class.
	 * 
	 * @param <T>
	 *            The class provided to {@code cls}.
	 * 
	 * @param cls
	 *            The class of which an instance is to be supplied, or {@code null}.
	 * @return An instance of the specified class, or {@code null} if it is impossible to obtain it (e. g. the class does not exist or {@code cls} is {@code null}:
	 */
	public static final <T> T buildInstanceOf(final Class<T> cls) {
		if (cls == null)
			return null;

		if (cls.isEnum()) {
			final T[] enumConstants = cls.getEnumConstants();
			return enumConstants.length > 0 ? enumConstants[0] : null;
		}

		return Utilities.newInstance(cls);
	}

	/**
	 * Creates an instance of the specified class, even when the constructor is private.
	 * 
	 * @param cls
	 *            The class of which an instance is to be created. Must have a (possibly private) no-arguments constructor.
	 * @return The created instance, or {@code null} if it is impossible to create it.
	 */
	private static final <T> T newInstance(final Class<T> cls) {
		try {
			final Constructor<T> constructor = cls.getDeclaredConstructor();
			constructor.setAccessible(true); // Prevents exception in case the constructor is not public.
			return constructor.newInstance();
		} catch (final NoSuchMethodException e) {
			return null;
		} catch (final SecurityException e) {
			return null;
		} catch (final InstantiationException e) {
			return null;
		} catch (final IllegalAccessException e) {
			return null;
		} catch (final IllegalArgumentException e) {
			return null;
		} catch (final InvocationTargetException e) {
			return null;
		}
	}

	/**
	 * Expands an array by appending a second array.
	 * 
	 * @param firstArray
	 *            The array to expand by appending the second array.
	 * @param secondArray
	 *            The array to append to the first array.
	 * @return An array holding all elements of the first and second array, in the provided order.
	 */
	public static final <T> T[] append(final T[] firstArray, final T[] secondArray) {
		final T[] expandedArray = Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
		System.arraycopy(secondArray, 0, expandedArray, firstArray.length, secondArray.length);
		return expandedArray;
	}

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
