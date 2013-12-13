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

import static javax.xml.bind.annotation.XmlAccessType.NONE;
import static net.java.dev.webdav.util.Utilities.sameOrEqual;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * WebDAV responsedescription XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_responsedescription">Chapter 14.25 "responsedescription XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(NONE)
@XmlRootElement(name = "responsedescription")
public final class ResponseDescription {

	@XmlValue
	private String content;

	public final String getContent() {
		return this.content;
	}

	public final void setContent(final String content) {
		this.content = content;
	}

	@SuppressWarnings("unused")
	private ResponseDescription() {
		// For unmarshalling only.
	}

	public ResponseDescription(final String content) {
		this.content = content;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object == this)
			return true;

		if (!(object instanceof ResponseDescription))
			return false;

		final ResponseDescription that = (ResponseDescription) object;

		return sameOrEqual(this.content, that.content);
	}

	@Override
	public final int hashCode() {
		return this.content.hashCode();
	}
}
