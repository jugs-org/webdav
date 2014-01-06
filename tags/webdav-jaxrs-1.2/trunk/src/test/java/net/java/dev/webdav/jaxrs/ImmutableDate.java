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

package net.java.dev.webdav.jaxrs;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;

/**
 * Matches when date is immutable, i. e. invoking and setter either throws {@link UnsupportedOperationException} or does effectively not modify the date.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ImmutableDate extends TypeSafeMatcher<Date> {
	@SuppressWarnings("deprecation")
	@Override
	public final boolean matchesSafely(final Date date) {
		try {
			final Date originalDate = (Date) date.clone();
			final Date modifiedDate = date;
			modifiedDate.setDate(1);
			modifiedDate.setHours(1);
			modifiedDate.setMinutes(1);
			modifiedDate.setMonth(1);
			modifiedDate.setSeconds(1);
			modifiedDate.setTime(1);
			modifiedDate.setYear(1);
			return originalDate.equals(modifiedDate);
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
	public static final Matcher<Date> immutable() {
		return new ImmutableDate();
	}
}
