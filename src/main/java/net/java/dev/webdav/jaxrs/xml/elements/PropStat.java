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

import static net.java.dev.webdav.util.Utilities.notNull;
import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * WebDAV propstat XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_propstat">Chapter 14.22 "propstat XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlType(propOrder = { "prop", "status", "error", "responseDescription" })
@XmlRootElement(name = "propstat")
public final class PropStat {

	@XmlElement
	private final Prop prop;

	@XmlElement
	private final Status status;

	@XmlElement
	private final Error error;

	@XmlElement(name = "responsedescription")
	private final ResponseDescription responseDescription;

	@SuppressWarnings("unused")
	private PropStat() {
		this.prop = null;
		this.status = null;
		this.error = null;
		this.responseDescription = null;
	}

	public PropStat(final Prop prop, final Status status, final Error error, final ResponseDescription responseDescription) {
		this.prop = notNull(prop, "prop");
		this.status = notNull(status, "status");
		this.error = error;
		this.responseDescription = responseDescription;
	}

	public PropStat(final Prop prop, final Status status) {
		this(prop, status, null, null);
	}

	public PropStat(final Prop prop, final Status status, final Error error) {
		this(prop, status, error, null);
	}

	public PropStat(final Prop prop, final Status status, final ResponseDescription responseDescription) {
		this(prop, status, null, responseDescription);
	}

	public final Prop getProp() {
		return this.prop;
	}

	public final Status getStatus() {
		return this.status;
	}

	public final Error getError() {
		return this.error;
	}

	public final ResponseDescription getResponseDescription() {
		return this.responseDescription;
	}

	@Override
	public final int hashCode() {
		return this.prop.hashCode() ^ this.status.hashCode() ^ (this.error == null ? -1 : this.error.hashCode())
				^ (this.responseDescription == null ? -1 : this.responseDescription.hashCode());
	}

	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(this instanceof PropStat))
			return false;

		final PropStat that = (PropStat) o;

		return this.prop.equals(that.prop) && this.status.equals(that.status) && sameOrEqual(this.error, that.error)
				&& sameOrEqual(this.responseDescription, that.responseDescription);
	}
}
