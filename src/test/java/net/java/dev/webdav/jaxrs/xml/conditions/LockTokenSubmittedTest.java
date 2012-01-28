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

package net.java.dev.webdav.jaxrs.xml.conditions;

import static net.java.dev.webdav.jaxrs.Immutable.immutable;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.transform.XmlConverters.the;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link LockTokenSubmitted}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class LockTokenSubmittedTest {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsSoleArgument() {
		new LockTokenSubmitted(null);
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsFirstOfTwoArguments() {
		new LockTokenSubmitted(null, new HRef("x"));
	}

	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNullAsFirstOfUnlimitedArguments() {
		new LockTokenSubmitted(null, new HRef[] { new HRef("x") });
	}

	@DataPoints
	public static final Object[][] DATA_POINTS = new Object[][] {
		{ new LockTokenSubmitted(new HRef("x")), "<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href></D:lock-token-submitted>" },
		{ new LockTokenSubmitted(new HRef("x"), new HRef("y")),	"<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href></D:lock-token-submitted>" },
		{ new LockTokenSubmitted(new HRef("x"), new HRef[] { new HRef("y") }), "<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href></D:lock-token-submitted>" }
	};

	@Theory
	public final void marshalling(final Object[] dataPoint) {
		final Writer writer = new StringWriter();
		JAXB.marshal(dataPoint[0], writer);
		final String actualXml = writer.toString();
		final String expectedXml = (String) dataPoint[1];
		assertThat(the(actualXml), isEquivalentTo(the(expectedXml)));
	}

	@Theory
	public final void unmarshalling(final Object[] dataPoint) {
		final LockTokenSubmitted actualElement = JAXB.unmarshal(new StringReader((String) dataPoint[1]), LockTokenSubmitted.class);
		final LockTokenSubmitted expectedElement = (LockTokenSubmitted) dataPoint[0];
		assertThat(actualElement, is(equalTo(expectedElement)));
	}

	@Theory
	public final void hRefsAreSorted(final Object[] dataPoint) {
		final List<HRef> actualSequence = JAXB.unmarshal(new StringReader((String) dataPoint[1]), LockTokenSubmitted.class).getHRefs();
		final List<HRef> expectedSequence = ((LockTokenSubmitted) dataPoint[0]).getHRefs();
		assertThat(actualSequence, is(expectedSequence));
	}

	@Theory
	public final void hRefsAreEffectivelyImmutable(final Object[] dataPoint) {
		assertEffectivelyImmutableHRefs((LockTokenSubmitted) dataPoint[0]);
		assertEffectivelyImmutableHRefs(JAXB.unmarshal(new StringReader((String) dataPoint[1]), LockTokenSubmitted.class));
	}

	private static final void assertEffectivelyImmutableHRefs(final LockTokenSubmitted immutableObject) {
		final Collection<HRef> resultOfFirstCall = immutableObject.getHRefs();
		final Collection<HRef> resultOfSecondCall = immutableObject.getHRefs();
		assertThat(resultOfFirstCall, is(anyOf(immutable(HRef.class), not(sameInstance(resultOfSecondCall)))));
	}
}
