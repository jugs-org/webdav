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

import net.java.dev.webdav.jaxrs.versioning.xml.elements.SupportedLiveProperty;

/**
 * Versioning Extensions to WebDAV supported-live-property-set Property.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/deltav/protocol/rfc3253.html#PROPERTY_supported-live-property-set">Chapter 3.1.4 "DAV:supported-live-property-set (protected)" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
@XmlRootElement(name = "supported-live-property-set")
public final class SupportedLivePropertySet {

	@XmlElement(name = "supported-live-property")
	private LinkedList<SupportedLiveProperty> supportedLiveProperties;

	public SupportedLivePropertySet() {
		// Has no members.
	}

	public SupportedLivePropertySet(final SupportedLiveProperty... supportedLiveProperties) {
		this.supportedLiveProperties = new LinkedList<SupportedLiveProperty>(Arrays.asList(supportedLiveProperties));
	}

	@SuppressWarnings("unchecked")
	public final List<SupportedLiveProperty> getSupportedLiveProperties() {
		return (List<SupportedLiveProperty>) this.supportedLiveProperties.clone();
	}

}
