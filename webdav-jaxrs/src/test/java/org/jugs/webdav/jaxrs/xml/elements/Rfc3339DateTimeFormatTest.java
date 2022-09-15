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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.util.DateBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link Rfc3339DateTimeFormat}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class Rfc3339DateTimeFormatTest {

	public static final Object[][] DATA_POINTS = new Object[][] {
			{/* Assert that Z is UTC */DateBuilder.date(2012, 11, 12, 13, 14, 15, 167, "UTC"), "2012-11-12T13:14:15.167Z", "2012-11-12T13:14:15.167Z" },
			{/* Assert that numeric offset is working */DateBuilder.date(2012, 12, 31, 13, 14, 15, 167, "GMT-18:17"), "2012-12-31T13:14:15.167-18:17",
             "2013-01-01T07:31:15.167Z"/* format() always does Zulu time, which results in complex time value shifting */},
			{/* Assert that missing fraction is working */DateBuilder.date(2012, 11, 12, 13, 14, 15, 0, "UTC"), "2012-11-12T13:14:15Z", "2012-11-12T13:14:15.000Z" } };

	@ParameterizedTest(name = "[{index}]")
	@ValueSource(ints = {0, 1, 2})
	void parsing(int i) throws ParseException {
		parsing(DATA_POINTS[i]);
	}

	private void parsing(final Object[] dataPoint) throws ParseException {
		final Rfc3339DateTimeFormat rfc3339DateTimeFormat = new Rfc3339DateTimeFormat();
		final Date actual = rfc3339DateTimeFormat.parse((String) dataPoint[1]);
		final Date expected = (Date) dataPoint[0];
		assertThat(actual, is(expected));
	}

	@ParameterizedTest(name = "[{index}]")
	@ValueSource(ints = {0, 1, 2})
	void formatting(int i) {
		formatting(DATA_POINTS[i]);
	}

	private void formatting(final Object[] dataPoint) {
		final Rfc3339DateTimeFormat rfc3339DateTimeFormat = new Rfc3339DateTimeFormat();
		final String actual = rfc3339DateTimeFormat.format(dataPoint[0]);
		final String expected = (String) dataPoint[2];
		assertThat(actual, is(expected));
	}

	@Test
	void parsingFailsDueToMissingColonInOffset() {
		assertThrows(ParseException.class, () -> new Rfc3339DateTimeFormat().parse("2012-11-12T13:14:15.167+1200"));
	}
}
