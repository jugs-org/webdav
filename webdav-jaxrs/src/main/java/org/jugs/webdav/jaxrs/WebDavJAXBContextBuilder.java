/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import org.jugs.webdav.jaxrs.xml.conditions.CannotModifyProtectedProperty;
import org.jugs.webdav.jaxrs.xml.conditions.LockTokenMatchesRequestUri;
import org.jugs.webdav.jaxrs.xml.conditions.LockTokenSubmitted;
import org.jugs.webdav.jaxrs.xml.conditions.NoConflictingLock;
import org.jugs.webdav.jaxrs.xml.conditions.NoExternalEntities;
import org.jugs.webdav.jaxrs.xml.conditions.PreservedLiveProperties;
import org.jugs.webdav.jaxrs.xml.conditions.PropFindFiniteDepth;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.elements.Error;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.DisplayName;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLanguage;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetContentType;
import org.jugs.webdav.jaxrs.xml.properties.GetETag;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.jaxrs.xml.properties.LockDiscovery;
import org.jugs.webdav.jaxrs.xml.properties.ResourceType;
import org.jugs.webdav.jaxrs.xml.properties.SupportedLock;
import org.jugs.webdav.util.Utilities;

/**
 * Provides support for custom extensions to WebDAV, like custom Properties and XML Elements.<br>
 * 
 * WebDAV allows custom extensions for XML Elements and Properties. To enable JAX-RS to deal with these, each of them must be implemented as a JAXB class and
 * registered by passing it to the constructor of this factory.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#xml-extensibility">Chapter 17 "XML Extensibility in DAV" of RFC 2616
 *      "Hypertext Transfer Protocol -- HTTP/1.1"</a>
 */
final class WebDavJAXBContextBuilder {

	/**
	 * Builds a JAXB context for WebDAV.
	 * 
	 * @param auxiliaryClasses
	 *            Optional set of custom XML elements which shall get part of the context.
	 * @throws JAXBException
	 *             If JAXB cannot create the context.
	 */
	public static JAXBContext build(final Class<?>... auxiliaryClasses) throws JAXBException {
		final Class<?>[] webDavClasses = new Class<?>[] {ActiveLock.class, AllProp.class, CannotModifyProtectedProperty.class, Collection.class,
                                                         CreationDate.class, Depth.class, DisplayName.class, Error.class, Exclusive.class, GetContentLanguage.class, GetContentLength.class,
                                                         GetContentType.class, GetETag.class, GetLastModified.class, HRef.class, Include.class, Location.class, LockDiscovery.class, LockEntry.class,
                                                         LockInfo.class, LockRoot.class, LockScope.class, LockToken.class, LockTokenMatchesRequestUri.class, LockTokenSubmitted.class, LockType.class,
                                                         MultiStatus.class, NoConflictingLock.class, NoExternalEntities.class, Owner.class, PreservedLiveProperties.class, Prop.class,
                                                         PropertyUpdate.class, PropFind.class, PropFindFiniteDepth.class, PropName.class, PropStat.class, Remove.class, ResourceType.class,
                                                         Response.class, ResponseDescription.class, Set.class, Shared.class, Status.class, SupportedLock.class, TimeOut.class, Write.class,
														 DepthWrapper.class };
		final Class<?>[] allClasses = Utilities.append(webDavClasses, auxiliaryClasses);
		return JAXBContext.newInstance(allClasses);
	}
}
