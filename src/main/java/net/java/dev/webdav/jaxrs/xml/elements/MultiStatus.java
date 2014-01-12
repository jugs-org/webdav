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

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.hash;
import static net.java.dev.webdav.util.Utilities.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.java.dev.webdav.util.Utilities;

/**
 * WebDAV multistatus XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_multistatus">Chapter 14.16 "multistatus XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlType(propOrder = { "responses", "responseDescription" })
@XmlRootElement(name = "multistatus")
public final class MultiStatus {

	@XmlElement(name = "response")
	private final List<Response> responses;

	@XmlElement(name = "responsedescription")
	private final ResponseDescription responseDescription;

	public MultiStatus() {
		this.responses = new LinkedList<Response>();
		this.responseDescription = null;
	}

	public MultiStatus(final ResponseDescription responseDescription, final Response... responses) {
		this.responses = responses == null || responses.length == 0 ? Collections.<Response> emptyList() : asList(responses);
		this.responseDescription = responseDescription;
	}

	public MultiStatus(final Response... responses) {
		this(null, responses);
	}

	public MultiStatus(final ResponseDescription responseDescription) {
		this(responseDescription, (Response[]) null);
	}

	public final List<Response> getResponses() {
		return unmodifiableList(this.responses);
	}

	public final ResponseDescription getResponseDescription() {
		return this.responseDescription;
	}

	@Override
	public final int hashCode() {
		return hash(this.responses, this.responseDescription);
	}

	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof MultiStatus))
			return false;

		final MultiStatus that = (MultiStatus) o;

		return Arrays.equals(array(this.responses, this.responseDescription), array(that.responses, that.responseDescription));
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.responses, this.responseDescription);
	}
}
