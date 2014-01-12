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

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Unit test for {@link Rfc3339DateTimeFormat}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class Rfc3339DateTimeFormatTest {
	@DataPoints
	public static final Object[][] DATA_POINTS = new Object[][] {
			{/* Assert that Z is UTC */date(2012, 11, 12, 13, 14, 15, 167, "UTC"), "2012-11-12T13:14:15.167Z", "2012-11-12T13:14:15.167Z" },
			{/* Assert that numeric offset is working */date(2012, 12, 31, 13, 14, 15, 167, "GMT-18:17"), "2012-12-31T13:14:15.167-18:17",
					"2013-01-01T07:31:15.167Z" /* format() always does Zulu time, which results in complex time value shifting */},
			{/* Assert that missing fraction is working */date(2012, 11, 12, 13, 14, 15, 0, "UTC"), "2012-11-12T13:14:15Z", "2012-11-12T13:14:15.000Z" } };

	@Theory
	public final void parsing(final Object[] dataPoint) throws ParseException {
		final Rfc3339DateTimeFormat rfc3339DateTimeFormat = new Rfc3339DateTimeFormat();
		final Date actual = rfc3339DateTimeFormat.parse((String) dataPoint[1]);
		final Date expected = (Date) dataPoint[0];
		assertThat(actual, is(expected));
	}

	@Theory
	public final void formatting(final Object[] dataPoint) {
		final Rfc3339DateTimeFormat rfc3339DateTimeFormat = new Rfc3339DateTimeFormat();
		final String actual = rfc3339DateTimeFormat.format(dataPoint[0]);
		final String expected = (String) dataPoint[2];
		assertThat(actual, is(expected));
	}

	@Test(expected = ParseException.class)
	public final void parsingFailsDueToMissingColonInOffset() throws ParseException {
		new Rfc3339DateTimeFormat().parse("2012-11-12T13:14:15.167+1200");
	}
}
