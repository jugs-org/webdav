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
}
