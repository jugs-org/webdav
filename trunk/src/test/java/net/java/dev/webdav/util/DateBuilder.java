/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
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

package net.java.dev.webdav.util;

import static java.util.Calendar.MILLISECOND;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for conveniently building {@link Date} instances.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class DateBuilder {
	/**
	 * Builds a {@link Date} instance from a set of single integer values.
	 * 
	 * @param year
	 *            Year
	 * @param month
	 *            1...12
	 * @param day
	 *            1...31
	 * @param hour
	 *            0...23
	 * @param minute
	 *            0...59
	 * @param second
	 *            0...59
	 * @param millisecond
	 *            0...999
	 * @param timeZone
	 *            e. g. "UTC", "GMT+mm:nn" etc.
	 * @return {@link Date} instance reflecting the single integer values.
	 */
	public static final Date date(final int year, final int month, final int day, final int hour, final int minute, final int second, final int millisecond,
			final String timeZone) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		calendar.set(year, month - 1 /* Months count starts with zero! */, day, hour, minute, second);
		calendar.set(MILLISECOND, millisecond);
		return calendar.getTime();
	}
}
