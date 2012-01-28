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

package net.java.dev.webdav.jaxrs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;

/**
 * Matches when collection is immutable, i. e. invoking
 * {@link Collection#add(Object)} throws {@link UnsupportedOperationException}
 * or does effectively not extend the collection.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ImmutableCollection<E> extends TypeSafeMatcher<Collection<E>> {
	private final Class<E> elementType;

	public ImmutableCollection(final Class<E> elementType) {
		this.elementType = elementType;
	}

	@Override
	public final boolean matchesSafely(final Collection<E> collection) {
		try {
			final int sizeBeforeAdd = collection.size();
			collection.add(this.createElement());
			return sizeBeforeAdd == collection.size();
		} catch (final UnsupportedOperationException e) {
			return true;
		} catch (final Exception e) {
			Assert.fail(e.toString());
			return false;
		}
	}

	private final E createElement() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		final Constructor<E> defaultConstructor = this.elementType.getDeclaredConstructor();
		defaultConstructor.setAccessible(true);
		return defaultConstructor.newInstance();
	}

	@Override
	public final void describeTo(final Description description) {
		description.appendText("immutable");
	}

	@Factory
	public static final <E> Matcher<Collection<E>> immutable(final Class<E> elementType) {
		return new ImmutableCollection<E>(elementType);
	}
}