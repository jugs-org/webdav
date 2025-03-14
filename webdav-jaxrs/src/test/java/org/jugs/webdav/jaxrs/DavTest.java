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
package org.jugs.webdav.jaxrs;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Dav} class.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DavTest {

	@Test
	void shouldParseClassOne() {
		// given
		final String davHeaderValue = "1";
		// when
		final Dav dav = Dav.fromString(davHeaderValue);
		// then
		assertThat(dav, is(sameInstance(Dav.ONE)));
	}

	@Test
	void shouldParseClassOneTwo() {
		// given
		final String davHeaderValue = "1, 2";
		// when
		final Dav dav = Dav.fromString(davHeaderValue);
		// then
		assertThat(dav, is(sameInstance(Dav.ONE_TWO)));
	}

	@Test
	void shouldParseClassOneTwoThree() {
		// given
		final String davHeaderValue = "1, 2, 3";
		// when
		final Dav dav = Dav.fromString(davHeaderValue);
		// then
		assertThat(dav, is(sameInstance(Dav.ONE_TWO_THREE)));
	}

	@Test
	void shouldParseClassOneThree() {
		// given
		final String davHeaderValue = "1, 3";
		// when
		final Dav dav = Dav.fromString(davHeaderValue);
		// then
		assertThat(dav, is(sameInstance(Dav.ONE_THREE)));
	}

	@Test
	void shouldParseTokensAndUrls() {
		// given
		final String davHeaderValue = "SomeToken, SomeOtherToken, schema://host:port/path";
		// when
		final Dav dav = Dav.fromString(davHeaderValue);
		// then
		assertThat(dav, contains("SomeToken", "SomeOtherToken", "schema://host:port/path"));
	}

	@Test
	void shouldTreatUnorderedClassesAsEqualToOrderedClasses() {
		// given
		final String orderedClasses = "1, 2, 3";
		final String unorderedClasses = "2, 3, 1";
		// when
		final Dav orderedResult = Dav.fromString(orderedClasses);
		final Dav unorderedResult = Dav.fromString(unorderedClasses);
		// then
		assertThat(unorderedResult, is(equalTo(orderedResult)));
		assertThat(unorderedResult.hashCode(), is(equalTo(orderedResult.hashCode())));
	}

	@Test
	void shouldEnsureClassTwoAlsoContainsClassOne() {
		String davHeaderValue = "2";
		assertThrows(IllegalArgumentException.class, () -> new Dav(davHeaderValue));
	}

	@Test
	void shouldEnsureClassThreeAlsoContainsClassOne() {
		String davHeaderValue = "3";
		assertThrows(IllegalArgumentException.class, () -> new Dav(davHeaderValue));
	}

	@Test
	void shouldFindClassInClasses() {
		// given
		final Dav dav = new Dav("A", "B", "C");
		// when
		final boolean containsB = dav.contains("B");
		// then
		assertThat(containsB, is(true));
	}

	@Test
	void shouldProduceCommaSeparatedString() {
		// given
		final Dav dav = new Dav("A", "B", "C");
		// when
		final String asString = dav.toString();
		// then
		assertThat(asString, is(equalTo("A, B, C")));
	}

	@Test
	void shouldProduceEmptyStringForEmptyClasses() {
		// given
		final Dav dav = new Dav();
		// when
		final String asString = dav.toString();
		// then
		assertThat(asString.isEmpty(), is(true));
	}

	@Test
	public void shouldNotBeEqualToOtherClass() {
		// given
		final Dav dav = new Dav();
		// when
		final boolean isEqualToOtherClass = dav.equals(new Object());
		// then
		assertThat(isEqualToOtherClass, is(false));
	}

	@Test
	void testToString() {
		assertEquals("1, 2, 3", Dav.ONE_TWO_THREE.toString());
	}

}
