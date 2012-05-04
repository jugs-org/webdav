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
		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(MILLISECOND, millisecond);
		return calendar.getTime();
	}
}
