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

package org.jugs.webdav.util;

import static java.util.Calendar.MILLISECOND;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Unit test for {@link DateBuilder}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DateBuilderTest {
	@Test
	public final void date() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
		calendar.set(2012, 11 - 1 /* Months count starts with zero! */, 12, 13, 14, 15);
		calendar.set(MILLISECOND, 16);
		final Date expectedDate = calendar.getTime();
		assertThat(DateBuilder.date(2012, 11, 12, 13 - 1 /* Europe/Berlin = UTC+1 */, 14, 15, 16, "UTC"), is(expectedDate));
	}
}
