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

package org.jugs.webdav.jaxrs.xml.conditions;

import org.hamcrest.CoreMatchers;
import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.jugs.webdav.jaxrs.ImmutableCollection;
import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.jaxrs.xml.elements.HRef;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link LockTokenSubmitted}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class LockTokenSubmittedTest extends AbstractJaxbCoreFunctionality<LockTokenSubmitted> {
	private static final HRef HREF = new HRef("x");

	@Test
	void constructorDoesNotAcceptNullAsSoleArgument() {
		assertThrows(NullArgumentException.class, () -> new LockTokenSubmitted(null));
	}

	@Test
	void constructorDoesNotAcceptNullAsFirstOfTwoArguments() {
		assertThrows(NullArgumentException.class, () -> new LockTokenSubmitted(null, HREF));
	}

	@Test
	public final void constructorDoesNotAcceptNullAsFirstOfUnlimitedArguments() {
		assertThrows(NullArgumentException.class, () -> new LockTokenSubmitted(null, new HRef[] { HREF }));
	}

	@DataPoints
	public static final Object[][] DATA_POINTS = new Object[][] {
			{ new LockTokenSubmitted(HREF), "<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href></D:lock-token-submitted>" },
			{ new LockTokenSubmitted(new HRef("x"), new HRef("y")),
					"<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href></D:lock-token-submitted>" },
			{ new LockTokenSubmitted(new HRef("x"), new HRef[] { new HRef("y"), new HRef("z") }),
					"<D:lock-token-submitted xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href><D:href>z</D:href></D:lock-token-submitted>" } };

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
		assertThat(resultOfFirstCall, is(CoreMatchers.anyOf(ImmutableCollection.immutable(HRef.class), not(sameInstance(resultOfSecondCall)))));
	}

	@Override
	protected final LockTokenSubmitted getInstance() {
		return new LockTokenSubmitted(HREF);
	}

	@Override
	protected final String getString() {
		return "LockTokenSubmitted[[HRef[x]]]";
	}
}
