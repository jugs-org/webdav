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

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.Collections.singleton;
import static javax.xml.bind.annotation.XmlAccessType.NONE;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;
import net.java.dev.webdav.jaxrs.Headers;
import net.java.dev.webdav.jaxrs.xml.elements.TimeOut.Adapter;
import net.java.dev.webdav.util.Utilities;

/**
 * WebDAV timeout XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_timeout">Chapter 14.29 "timeout XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(NONE)
@XmlRootElement(name = "timeout")
@XmlJavaTypeAdapter(Adapter.class)
public final class TimeOut {
	private static final long INFINITE_VALUE = MAX_VALUE;

	private static final String INFINITE_TOKEN = "Infinite";

	private static final String SECOND_TOKEN = "Second-%d";

	public static final TimeOut INFINITE = new TimeOut();

	/**
	 * The number of seconds, or {@link #INFINITE_VALUE} for infinite timeout.
	 */
	private long timeType;

	@XmlValue
	private final String getTimeType() {
		return this.timeType == INFINITE_VALUE ? INFINITE_TOKEN : format(SECOND_TOKEN, this.timeType);
	}

	@SuppressWarnings("unused")
	private final void setTimeType(final String timeType) {
		this.timeType = isInfinite(timeType) ? INFINITE_VALUE : parseSecond(timeType);
	}

	private static boolean isInfinite(final String timeType) {
		return INFINITE_TOKEN.equals(timeType);
	}

	private static long parseSecond(final String timeType) {
		return parseLong(timeType.substring(timeType.lastIndexOf('-') + 1));
	}

	private TimeOut() {
		this(INFINITE_VALUE);
	}

	public TimeOut(final long seconds) {
		this.timeType = seconds;
	}

	public final boolean isInfinite() {
		return this.timeType == INFINITE_VALUE;
	}

	/**
	 * @return The duration of the timeout in seconds, or {@link Long#MAX_VALUE} for infinity. Note that future versions will return null for infinity.
	 */
	public final long getSeconds() {
		return this.timeType;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof TimeOut))
			return false;

		final TimeOut that = (TimeOut) object;

		return this.timeType == that.timeType;
	}

	@Override
	public final int hashCode() {
		return String.valueOf(this.timeType).hashCode();
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<TimeOut> {
		@Override
		protected final Collection<TimeOut> getConstants() {
			return singleton(INFINITE);
		}
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.timeType);
	}

	/**
	 * Factory method creating {@link TimeOut} instances from {@code String} value representations (e. g. as used in HTTP {@link Headers#TIMEOUT} header).
	 * Guarantees that {@link #INFINITE} singleton is returned for {@code "Infinite"} string, hence allowing to compare for infinity using {@code ==}
	 * comparison.
	 * <p>
	 * Example:<br/>
	 * <code>
	 * TimeOut to = TimeOut.valueOf("Infinite");<br/>
	 * if (to == Timeout.INFINITE) { ... }<br/>
	 * </code>
	 * </p>
	 * 
	 * @param timeType
	 *            Either {@code Second-n} (where {@code n} is the length of the timeout) or {@code Infinite}.
	 * @return An instance of {@link TimeOut} with the length taken from the {@code timeOutHeader} string. Instance is guaranteed to be {@link #INFINITE} in
	 *         case {@code timeOutHeader} is {@code "Infinity"}.
	 * @since 1.2.1
	 */
	public static final TimeOut valueOf(final String timeType) {
		return isInfinite(timeType) ? INFINITE : new TimeOut(parseSecond(timeType));
	}
}
