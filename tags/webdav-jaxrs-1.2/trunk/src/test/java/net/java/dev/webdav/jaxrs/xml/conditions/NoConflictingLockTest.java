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

package net.java.dev.webdav.jaxrs.xml.conditions;

import static net.java.dev.webdav.jaxrs.ImmutableCollection.immutable;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

/**
 * Unit test for {@link NoConflictingLock}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class NoConflictingLockTest extends AbstractJaxbCoreFunctionality<NoConflictingLock> {
	private static final HRef HREF = new HRef("x");

	@DataPoints
	public static final Object[][] DATA_POINTS = new Object[][] {
			{ new NoConflictingLock(), "<D:no-conflicting-lock xmlns:D=\"DAV:\"/>" },
			{ new NoConflictingLock(HREF), "<D:no-conflicting-lock xmlns:D=\"DAV:\"><D:href>x</D:href></D:no-conflicting-lock>" },
			{ new NoConflictingLock(new HRef("x"), new HRef("y"), new HRef("z")),
					"<D:no-conflicting-lock xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href><D:href>z</D:href></D:no-conflicting-lock>" },
			{ new NoConflictingLock(new HRef[] { new HRef("x"), new HRef("y"), new HRef("z") }),
					"<D:no-conflicting-lock xmlns:D=\"DAV:\"><D:href>x</D:href><D:href>y</D:href><D:href>z</D:href></D:no-conflicting-lock>" } };

	@Theory
	public final void hRefsAreSorted(final Object[] dataPoint) {
		final List<HRef> actualSequence = JAXB.unmarshal(new StringReader((String) dataPoint[1]), NoConflictingLock.class).getHRefs();
		final List<HRef> expectedSequence = ((NoConflictingLock) dataPoint[0]).getHRefs();
		assertThat(actualSequence, is(expectedSequence));
	}

	@Theory
	public final void hRefsAreEffectivelyImmutable(final Object[] dataPoint) {
		assertEffectivelyImmutableHRefs((NoConflictingLock) dataPoint[0]);
		assertEffectivelyImmutableHRefs(JAXB.unmarshal(new StringReader((String) dataPoint[1]), NoConflictingLock.class));
	}

	private static final void assertEffectivelyImmutableHRefs(final NoConflictingLock immutableObject) {
		final Collection<HRef> resultOfFirstCall = immutableObject.getHRefs();
		final Collection<HRef> resultOfSecondCall = immutableObject.getHRefs();
		assertThat(resultOfFirstCall, is(anyOf(immutable(HRef.class), not(sameInstance(resultOfSecondCall)))));
	}

	@Override
	protected final NoConflictingLock getInstance() {
		return new NoConflictingLock(HREF);
	}

	@Override
	protected final String getString() {
		return "NoConflictingLock[[HRef[x]]]";
	}
}
