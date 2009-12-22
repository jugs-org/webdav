/*
 * Copyright 2009 Markus KARG
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.java.dev.webdav.jaxrs.versioning.xml.properties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.versioning.xml.elements.SupportedMethod;

/**
 * Versioning Extensions to WebDAV supported-method-set Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/deltav/protocol/rfc3253.html#PROPERTY_supported-method-set">Chapter 3.1.3 "DAV:supported-method-set (protected)" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
@XmlRootElement(name = "supported-method-set")
public final class SupportedMethodSet {

	@XmlElement(name = "supported-method")
	private LinkedList<SupportedMethod> supportedMethods;

	public SupportedMethodSet() {
		// Keeping defaults by intention.
	}

	public SupportedMethodSet(final SupportedMethod... supportedMethods) {
		this.supportedMethods = new LinkedList<SupportedMethod>(Arrays.asList(supportedMethods));
	}

	@SuppressWarnings("unchecked")
	public final List<SupportedMethod> getSupportedMethods() {
		return (List<SupportedMethod>) this.supportedMethods.clone();
	}

}
