/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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
package java.util;

/**
 * Backport of {@code java.util.Objects#hash(Object...)} and {@code java.util.Objects#hashCode()} from Java 7 into Java 6.
 * 
 * @author markus
 */
public final class Objects {
	/**
	 * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were placed into an array, and that array
	 * were hashed by calling {@link Arrays#hashCode(Object[])}.
	 * 
	 * <p>
	 * This method is useful for implementing {@link Object#hashCode()} on objects containing multiple fields. For example, if an object that has three fields,
	 * {@code x}, {@code y}, and {@code z}, one could write:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * &#064;Override
	 * public int hashCode() {
	 * 	return Objects.hash(x, y, z);
	 * }
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param values
	 *            the values to be hashed
	 * @return a hash value of the sequence of input values
	 * @see Arrays#hashCode(Object[])
	 * @see List#hashCode
	 */
	public static int hash(final Object... values) {
		return Arrays.hashCode(values);
	}

	/**
	 * Returns the hash code of a non-{@code null} argument and 0 for a {@code null} argument.
	 * 
	 * @param o
	 *            an object
	 * @return the hash code of a non-{@code null} argument and 0 for a {@code null} argument
	 * @see Object#hashCode
	 */
	public static int hashCode(final Object o) {
		return o != null ? o.hashCode() : 0;
	}
}
