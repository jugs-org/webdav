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

package net.java.dev.webdav.jaxrs.xml.elements;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * WebDAV timeout XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_timeout">Chapter 14.29 "timeout XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlRootElement(name = "timeout")
public final class TimeOut {

	private static final String INFINITE_TOKEN = "Infinite";

	public static final TimeOut INFINITE = new TimeOut(INFINITE_TOKEN);

	@XmlValue
	private String timeType;

	@SuppressWarnings("unused")
	private TimeOut() {
		// For unmarshalling only.
	}

	private TimeOut(final String content) {
		this.timeType = content;
	}

	public TimeOut(final long seconds) {
		this.timeType = String.format("Second-%d", seconds);
	}

	public final boolean isInfinite() {
		return this.timeType.equals(INFINITE_TOKEN);
	}

	public final long getSeconds() {
		return this.isInfinite() ? Long.MAX_VALUE : Long.valueOf(this.timeType.substring(this.timeType.indexOf("-") + 1));
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof TimeOut))
			return false;

		final TimeOut that = (TimeOut) object;

		return this.timeType.equals(that.timeType);
	}

	@Override
	public final int hashCode() {
		return this.timeType.hashCode();
	}
}
