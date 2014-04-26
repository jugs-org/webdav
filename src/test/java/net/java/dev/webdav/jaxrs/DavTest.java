package net.java.dev.webdav.jaxrs;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Unit test for {@link Dav} class.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DavTest {
	@Test
	public final void shouldParseClassOne() {
		// given
		final String davHeaderValue = "1";

		// when
		final Dav dav = Dav.fromString(davHeaderValue);

		// then
		assertThat(dav, is(sameInstance(Dav.ONE)));
	}

	@Test
	public final void shouldParseClassOneTwo() {
		// given
		final String davHeaderValue = "1, 2";

		// when
		final Dav dav = Dav.fromString(davHeaderValue);

		// then
		assertThat(dav, is(sameInstance(Dav.ONE_TWO)));
	}

	@Test
	public final void shouldParseClassOneTwoThree() {
		// given
		final String davHeaderValue = "1, 2, 3";

		// when
		final Dav dav = Dav.fromString(davHeaderValue);

		// then
		assertThat(dav, is(sameInstance(Dav.ONE_TWO_THREE)));
	}

	@Test
	public final void shouldParseClassOneThree() {
		// given
		final String davHeaderValue = "1, 3";

		// when
		final Dav dav = Dav.fromString(davHeaderValue);

		// then
		assertThat(dav, is(sameInstance(Dav.ONE_THREE)));
	}

	@Test
	public final void shouldParseTokensAndUrls() {
		// given
		final String davHeaderValue = "SomeToken, SomeOtherToken, schema://host:port/path";

		// when
		final Dav dav = Dav.fromString(davHeaderValue);

		// then
		assertThat(dav, contains("SomeToken", "SomeOtherToken", "schema://host:port/path"));
	}

	@Test
	public final void shouldTreatUnorderedClassesAsEqualToOrderedClasses() {
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

	@Test(expected = IllegalArgumentException.class)
	public final void shouldEnsureClassTwoAlsoContainsClassOne() {
		// given
		final String davHeaderValue = "2";

		// when
		new Dav(davHeaderValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void shouldEnsureClassThreeAlsoContainsClassOne() {
		// given
		final String davHeaderValue = "3";

		// when
		new Dav(davHeaderValue);
	}

	@Test
	public final void shouldFindClassInClasses() {
		// given
		final Dav dav = new Dav("A", "B", "C");

		// when
		final boolean containsB = dav.contains("B");

		// then
		assertThat(containsB, is(true));
	}

	@Test
	public final void shouldProduceCommaSeparatedString() {
		// given
		final Dav dav = new Dav("A", "B", "C");

		// when
		final String asString = dav.toString();

		// then
		assertThat(asString, is(equalTo("A, B, C")));
	}

	@Test
	public final void shouldProduceEmptyStringForEmptyClasses() {
		// given
		final Dav dav = new Dav();

		// when
		final String asString = dav.toString();

		// then
		assertThat(asString.isEmpty(), is(true));
	}

	@Test
	public final void shouldNotBeEqualToOtherClass() {
		// given
		final Dav dav = new Dav();

		// when
		final boolean isEqualToOtherClass = dav.equals(new Object());

		// then
		assertThat(isEqualToOtherClass, is(false));
	}
}
