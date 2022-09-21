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

package org.jugs.webdav.jaxrs;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collection;

/**
 * Matches when collection is immutable, i. e. invoking {@link Collection#add(Object)} throws {@link UnsupportedOperationException} or does effectively not
 * extend the collection.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ImmutableCollection<E> extends TypeSafeMatcher<Collection<E>> {

	private final Class<E> elementType;

	public ImmutableCollection(final Class<E> elementType) {
		this.elementType = elementType;
	}

	@Override
	public boolean matchesSafely(final Collection<E> collection) {
		try {
			final int sizeBeforeAdd = collection.size();
			collection.add(createElementType());
			return sizeBeforeAdd == collection.size();
		} catch (final UnsupportedOperationException e) {
			return true;
		}
	}

	private E createElementType() {
		try {
			return this.elementType.getDeclaredConstructor(String.class).newInstance("");
		} catch (ReflectiveOperationException ex) {
			throw new IllegalArgumentException("unsupported type: " + this.elementType, ex);
		}
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("immutable");
	}

	@Factory
	public static <E> Matcher<Collection<E>> immutable(final Class<E> elementType) {
		return new ImmutableCollection<>(elementType);
	}
}
