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

package org.jugs.webdav.jaxrs.xml.properties;

import org.jugs.webdav.jaxrs.ConstantsAdapter;
import org.jugs.webdav.jaxrs.xml.elements.Collection;
import org.jugs.webdav.util.Utilities;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.jugs.webdav.util.Utilities.notNull;

/**
 * WebDAV resourcetype Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_resourcetype">Chapter 15.9 "resourcetype Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(ResourceType.Adapter.class)
@XmlRootElement(name = "resourcetype")
public final class ResourceType {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final ResourceType RESOURCETYPE = new ResourceType();

	@XmlAnyElement(lax = true)
	private final List<Object> resourceTypes;

	public static final ResourceType COLLECTION = new ResourceType(Collection.COLLECTION);

	/**
	 * @deprecated Since 1.2. Use {@link #RESOURCETYPE} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
	public ResourceType() {
		this.resourceTypes = new LinkedList<>();
	}

	/**
	 * Create a resource type from an array of resource types.
	 *
	 * @param resourceTypes array of resource types
	 */
	public ResourceType(final Object... resourceTypes) {
		this.resourceTypes = asList(notNull(resourceTypes, "resourceTypes"));
	}

	/**
	 * @deprecated Since 1.2 use {@link #getResourceTypes()} instead. This method will not be supported anymore in future releases.
	 * @return The same result as {@link #getResourceTypes()}
	 */
	@Deprecated
	 public List<Object> getResourceType() {
		return this.getResourceTypes();
	}

	/**
	 * Get a list of resource types.
	 *
	 * @return a list of resource types.
	 */
	 public List<Object> getResourceTypes() {
		return unmodifiableList(this.resourceTypes);
	}

	@Override
	 public int hashCode() {
		return this.resourceTypes.hashCode();
	}

	@Override
	 public boolean equals(final Object other) {
		if (!(other instanceof ResourceType))
			return false;

		final ResourceType that = (ResourceType) other;

		return this.resourceTypes.equals(that.resourceTypes);
	}

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<ResourceType> {
		@Override
		protected java.util.Collection<ResourceType> getConstants() {
			return asList(RESOURCETYPE, COLLECTION);
		}
	}

	@Override
	 public String toString() {
		return Utilities.toString(this, this.resourceTypes);
	}
}
