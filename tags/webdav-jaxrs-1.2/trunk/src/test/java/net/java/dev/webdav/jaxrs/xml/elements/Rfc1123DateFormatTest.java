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

package net.java.dev.webdav.jaxrs.xml.elements;

import static net.java.dev.webdav.util.DateBuilder.date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Date;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link Rfc1123DateFormat}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class Rfc1123DateFormatTest {
	@DataPoint
	public static final Object[] DATA_POINTS = new Object[] { date(2012, 11, 12, 13 + 1 /* Europe/Berlin = UTC+1 */, 14, 15, 16, "Europe/Berlin"),
			"Mon, 12 Nov 2012 13:14:15 GMT",
			date(2012, 11, 12, 13 + 1 /* Europe/Berlin = UTC+1 */, 14, 15, 0 /* RFC1123 ignores milliseconds */, "Europe/Berlin") };

	@Theory
	public final void parsing(final Object[] dataPoint) throws ParseException {
		final Rfc1123DateFormat rfc1123DateFormat = new Rfc1123DateFormat();
		final Date actual = rfc1123DateFormat.parse((String) dataPoint[1]);
		final Date expected = (Date) dataPoint[2]; // RFC1123 ignores milliseconds, so dataPoint[0] cannot be used here.
		assertThat(actual, is(expected));
	}

	@Theory
	public final void formatting(final Object[] dataPoint) {
		final Rfc1123DateFormat rfc1123DateFormat = new Rfc1123DateFormat();
		final String actual = rfc1123DateFormat.format(dataPoint[0]);
		final String expected = (String) dataPoint[1];
		assertThat(actual, is(expected));
	}
}
