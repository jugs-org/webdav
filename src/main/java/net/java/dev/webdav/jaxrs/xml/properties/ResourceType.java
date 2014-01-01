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

package net.java.dev.webdav.jaxrs.xml.properties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.NullArgumentException;
import net.java.dev.webdav.jaxrs.xml.elements.Collection;

/**
 * WebDAV resourcetype Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_resourcetype">Chapter 15.9 "resourcetype Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "resourcetype")
public final class ResourceType {

	@XmlAnyElement(lax = true)
	private LinkedList<Object> resourceTypes;

	public static final ResourceType COLLECTION = new ResourceType(new Collection());

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used
	 * for creation of valid instances of this property; use {@link #ResourceType(Object...)} instead.
	 */
	public ResourceType() {
		this.resourceTypes = new LinkedList<Object>();
	}

	public ResourceType(final Object... resourceTypes) {
		if (resourceTypes == null)
			throw new NullArgumentException("resourceTypes");

		this.resourceTypes = new LinkedList<Object>(Arrays.asList(resourceTypes));
	}

	/**
	 * @deprecated Since 1.2 use {@link #getResourceTypes()} instead. This method will not be supported anymore in future releases.
	 * @return The same result as {@link #getResourceTypes()}
	 */
	@Deprecated
	public final List<Object> getResourceType() {
		return this.getResourceTypes();
	}

	@SuppressWarnings("unchecked")
	public final List<Object> getResourceTypes() {
		return (List<Object>) this.resourceTypes.clone();
	}

	@Override
	public final int hashCode() {
		return this.resourceTypes.hashCode();
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof ResourceType))
			return false;

		final ResourceType that = (ResourceType) other;

		return this.resourceTypes.equals(that.resourceTypes);
	}
}