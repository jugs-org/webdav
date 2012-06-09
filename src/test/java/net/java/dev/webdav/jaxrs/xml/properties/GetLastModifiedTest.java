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

package net.java.dev.webdav.jaxrs.xml.properties;

import static net.java.dev.webdav.jaxrs.ImmutableDate.immutable;
import static net.java.dev.webdav.util.DateBuilder.date;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.Date;

import javax.xml.bind.JAXB;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.AbstractJaxbCoreFunctionality;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theory;

/**
 * Unit test for {@link GetLastModified}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class GetLastModifiedTest extends AbstractJaxbCoreFunctionality<GetLastModified> {
	@Test(expected = NullArgumentException.class)
	public final void constructorDoesNotAcceptNull() {
		new GetLastModified(null);
	}

	@DataPoint
	public static final Object[] NO_ARGS_CONSTRUCTOR = new Object[] { new GetLastModified(), "<D:getlastmodified xmlns:D=\"DAV:\"/>", null };

	@DataPoint
	public static final Object[] DATE_CONSTRUCTOR = new Object[] { new GetLastModified(date(2012, 11, 12, 13, 14, 15, 0, "GMT")),
			"<D:getlastmodified xmlns:D=\"DAV:\">Mon, 12 Nov 2012 13:14:15 GMT</D:getlastmodified>", date(2012, 11, 12, 13, 14, 15, 0, "GMT") };

	@Override
	protected final void assertThatGettersProvideExpectedValues(final GetLastModified actual, final GetLastModified expected, final Object[] dataPoint) {
		assertThat(actual.getDateTime(), is(dataPoint[2]));
		assertThat(expected.getDateTime(), is(dataPoint[2]));
	}

	@Theory
	public final void dateIsEffectivelyImmutable(final Object[] dataPoint) {
		assertEffectivelyImmutableDate((GetLastModified) dataPoint[0]);
		assertEffectivelyImmutableDate(JAXB.unmarshal(new StringReader((String) dataPoint[1]), GetLastModified.class));
	}

	private static final void assertEffectivelyImmutableDate(final GetLastModified immutableObject) {
		final Date resultOfFirstCall = immutableObject.getDateTime();
		final Date resultOfSecondCall = immutableObject.getDateTime();
		assertThat(resultOfFirstCall, is(anyOf(immutable(), not(sameInstance(resultOfSecondCall)), nullValue())));
	}
}
