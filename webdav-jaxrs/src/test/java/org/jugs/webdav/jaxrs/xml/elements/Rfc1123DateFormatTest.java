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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.util.DateBuilder;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for {@link Rfc1123DateFormat}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Rfc1123DateFormatTest {

	private static final Object[] DATA_POINTS = new Object[] {DateBuilder.date(2012, 11, 12, 13 + 1 /* Europe/Berlin = UTC+1 */, 14, 15, 16, "Europe/Berlin"),
                                                             "Mon, 12 Nov 2012 13:14:15 GMT",
                                                             DateBuilder.date(2012, 11, 12, 13 + 1 /* Europe/Berlin = UTC+1 */, 14, 15, 0 /* RFC1123 ignores milliseconds */, "Europe/Berlin") };

	@Test
	void parsing() throws ParseException {
		parsing(DATA_POINTS);
	}

	private void parsing(final Object[] dataPoint) throws ParseException {
		final Rfc1123DateFormat rfc1123DateFormat = new Rfc1123DateFormat();
		final Date actual = rfc1123DateFormat.parse((String) dataPoint[1]);
		final Date expected = (Date) dataPoint[2]; // RFC1123 ignores milliseconds, so dataPoint[0] cannot be used here.
		assertThat(actual, is(expected));
	}

	@Test
	void formatting() {
		formatting(DATA_POINTS);
	}

	private void formatting(final Object[] dataPoint) {
		final Rfc1123DateFormat rfc1123DateFormat = new Rfc1123DateFormat();
		final String actual = rfc1123DateFormat.format(dataPoint[0]);
		final String expected = (String) dataPoint[1];
		assertThat(actual, is(expected));
	}

}
