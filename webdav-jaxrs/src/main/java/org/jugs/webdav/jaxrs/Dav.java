/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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
package org.jugs.webdav.jaxrs;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Iterator;

/**
 * WebDAV DAV Header
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#HEADER_DAV">Chapter 10.1 "DAV Header" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 * 
 * @since 2.0
 */
public final class Dav implements Iterable<String> {
	public static final Dav ONE = new Dav(Headers.DAV_1);

	public static final Dav ONE_TWO = new Dav(Headers.DAV_1, Headers.DAV_2);

	public static final Dav ONE_TWO_THREE = new Dav(Headers.DAV_1, Headers.DAV_2, Headers.DAV_3);

	public static final Dav ONE_THREE = new Dav(Headers.DAV_1, Headers.DAV_3);

	private final Collection<String> complianceClasses;

	Dav(final String... complianceClasses) {
		ensureClassesTwoAndThreeAlsoContainClassOne(asList(complianceClasses));
		this.complianceClasses = asList(complianceClasses);
	}

	private static final void ensureClassesTwoAndThreeAlsoContainClassOne(final Collection<String> complianceClasses) {
		if ((complianceClasses.contains(Headers.DAV_2) || complianceClasses.contains(Headers.DAV_3)) && !complianceClasses.contains(
                Headers.DAV_1))
			throw new IllegalArgumentException("DAV compliance classes 2 and 3 must only be used together with compliance class 1.");
	}

	@Override
	public final Iterator<String> iterator() {
		return this.complianceClasses.iterator();
	}

	public final boolean contains(final String complianceClass) {
		return this.complianceClasses.contains(complianceClass);
	}

	public static final Dav fromString(final String complianceClasses) {
		final String[] tokens = complianceClasses.replace(" ", "").split(",");
		final Dav dav = new Dav(tokens);
		if (dav.equals(ONE))
			return ONE;
		if (dav.equals(ONE_TWO))
			return ONE_TWO;
		if (dav.equals(ONE_TWO_THREE))
			return ONE_TWO_THREE;
		if (dav.equals(ONE_THREE))
			return ONE_THREE;
		return dav;
	}

	@Override
	public final String toString() {
		return asCommaSeparatedString(this.complianceClasses);
	}

	private static final String asCommaSeparatedString(final Collection<String> collection) {
		if (collection.isEmpty())
			return "";

		final StringBuilder stringBuilder = new StringBuilder();
		for (final String complianceClass : collection)
			stringBuilder.append(", ").append(complianceClass);
		return stringBuilder.substring(2);
	}

	@Override
	public final int hashCode() {
		return this.complianceClasses.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof Dav))
			return false;

		final Dav that = (Dav) other;

		return haveEqualItems(this.complianceClasses, that.complianceClasses);
	}

	/**
	 * @param someCollection
	 *            must not be {@code null}.
	 * @param anotherCollection
	 *            must not be {@code null}.
	 * @return {@code true} when both collections are of the same length and contain the same items, independent of ther order; {@code false} otherwise.
	 */
	private static final <T> boolean haveEqualItems(final Collection<T> someCollection, final Collection<T> anotherCollection) {
		return someCollection.size() == anotherCollection.size() && someCollection.containsAll(anotherCollection);
	}
}
