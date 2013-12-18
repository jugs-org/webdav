/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

package net.java.dev.webdav.jaxrs;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.powermock.reflect.Whitebox;

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
	public final boolean matchesSafely(final Collection<E> collection) {
		try {
			final int sizeBeforeAdd = collection.size();
			collection.add(Whitebox.newInstance(this.elementType));
			return sizeBeforeAdd == collection.size();
		} catch (final UnsupportedOperationException e) {
			return true;
		} catch (final Exception e) {
			Assert.fail(e.toString());
			return false;
		}
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
