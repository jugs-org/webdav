/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.hash;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static org.jugs.webdav.util.Utilities.append;
import static org.jugs.webdav.util.Utilities.array;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jugs.webdav.jaxrs.NullArgumentException;
import org.jugs.webdav.util.Utilities;

/**
 * WebDAV response XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_response">Chapter 14.24 "response XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "hRefs", "status", "propStats", "error", "responseDescription", "location" })
@XmlRootElement
public final class Response {

	@XmlElement(name = "href")
	private final List<HRef> hRefs;

	private final Status status;

	@XmlElement(name = "propstat")
	private final List<PropStat> propStats;

	private final Error error;

	@XmlElement(name = "responsedescription")
	private ResponseDescription responseDescription;

	private Location location;

	@SuppressWarnings("unused")
	private Response() {
		this(new LinkedList<HRef>(), null, new LinkedList<PropStat>(), null, null, null);
	}

	private Response(final List<HRef> hRefs, final Status status, final List<PropStat> propStats, final Error error,
			final ResponseDescription responseDescription, final Location location) {
		this.hRefs = hRefs;
		this.status = status;
		this.propStats = propStats;
		this.error = error;
		this.responseDescription = responseDescription;
		this.location = location;
	}

	public Response(final HRef hRef, final Error error, final ResponseDescription responseDescription, final Location location, final PropStat propStat,
			final PropStat... propStats) {
		this(singletonList(hRef), null, append(notNull(propStat, "propStat"), propStats), error, responseDescription, location);
	}

	/**
	 * @deprecated Since 1.2, as the provided {@code propStats} collection is not necessarily immutable and there is no standard Java way to enforce
	 *             immutability. Use {@link #Response(HRef, Error, ResponseDescription, Location, PropStat, PropStat...)} instead.
	 * @since 1.1.1
	 */
	@Deprecated
	public Response(final HRef hRef, final Error error, final ResponseDescription responseDescription, final Location location,
			final Collection<PropStat> propStats) {
		this(singletonList(hRef), null, new ArrayList<PropStat>(notNull(propStats, "propStats")), error, responseDescription, location);

		if (propStats.isEmpty())
			throw new NullArgumentException("propStat");
	}

	public Response(final Status status, final Error error, final ResponseDescription responseDescription, final Location location, final HRef hRef,
			final HRef... hRefs) {
		this(append(notNull(hRef, "hRef"), hRefs), notNull(status, "status"), Collections.<PropStat> emptyList(), error, responseDescription, location);
	}

	public final List<HRef> getHRefs() {
		return unmodifiableList(this.hRefs);
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

	public final Location getLocation() {
		return this.location;
	}

	public final List<PropStat> getPropStats() {
		return unmodifiableList(this.propStats);
	}

	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Response))
			return false;

		final Response that = (Response) o;

		return Arrays.equals(array(this.hRefs, this.status, this.propStats, this.error, this.responseDescription, this.location),
				array(that.hRefs, that.status, that.propStats, that.error, that.responseDescription, that.location));
	}

	@Override
	public final int hashCode() {
		return hash(this.hRefs, this.status, this.propStats, this.error, this.responseDescription, this.location);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.status, this.propStats, this.error, this.responseDescription, this.location);
	}
}
