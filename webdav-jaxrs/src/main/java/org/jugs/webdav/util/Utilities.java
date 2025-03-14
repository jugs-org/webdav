/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jugs.webdav.jaxrs.NullArgumentException;

/**
 * Common purpose utilities.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Utilities {

	/**
	 * Supplies an instance of the specified class.
	 * 
	 * @param <T>
	 *            The class provided to {@code cls}.
	 * 
	 * @param cls
	 *            The class of which an instance is to be supplied, or {@code null}.
	 * @return An instance of the specified class, or {@code null} if it is impossible to obtain it (e. g. the class does not exist or {@code cls} is
	 *         {@code null}:
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
	 * Ensures that {@code t} is not {@code null}.
	 * 
	 * @param t
	 *            The argument to check.
	 * @param name
	 *            The name of the argument.
	 * @return {@code t}
	 * @throws NullArgumentException
	 *             in case {@code t} is {@code null}.
	 */
	public static final <T> T notNull(final T t, final String name) throws NullArgumentException {
		if (t == null)
			throw new NullArgumentException(name);

		return t;
	}

	/**
	 * Combines {@code t} and {@code ts} into one single list of size {@code 1 + ts.length}.
	 * 
	 * @param t
	 *            Set into the first position of the new list. Must not be {@code null}.
	 * @param ts
	 *            Appended starting at position {@code 1} into the new list. Must not be {@code null}.
	 * @return A new list containing first {@code t} then content of {@code ts}.
	 */
	public static final <T> List<T> append(T t, T... ts) {
		final List<T> result = new ArrayList<T>(1 + ts.length);
		result.add(t);
		result.addAll(asList(ts));
		return result;
	}

	/**
	 * Builds an array from varargs.
	 * 
	 * @param ts
	 *            Will become the content of the array.
	 * @return An array holding the complete content provided as varargs.
	 */
	public static final <T> T[] array(T... ts) {
		return ts;
	}
	
	/**
	 * Default implementation for {@link Object#toString()}. 
	 * @param object The object for which to produce the string.
	 * @param members The object's members for which to produce the string.
	 * @return A string of the form "Classname[memberValue, memberValue, ..., memberValue]";
	 */
	public static final String toString(final Object object, final Object... members) {
		return object.getClass().getSimpleName() + Arrays.toString(members);
	}
}
