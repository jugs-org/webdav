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

package net.java.dev.webdav.jaxrs.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.java.dev.webdav.jaxrs.xml.conditions.CannotModifyProtectedProperty;
import net.java.dev.webdav.jaxrs.xml.conditions.LockTokenMatchesRequestUri;
import net.java.dev.webdav.jaxrs.xml.conditions.LockTokenSubmitted;
import net.java.dev.webdav.jaxrs.xml.conditions.NoConflictingLock;
import net.java.dev.webdav.jaxrs.xml.conditions.NoExternalEntities;
import net.java.dev.webdav.jaxrs.xml.conditions.PreservedLiveProperties;
import net.java.dev.webdav.jaxrs.xml.conditions.PropFindFiniteDepth;
import net.java.dev.webdav.jaxrs.xml.elements.ActiveLock;
import net.java.dev.webdav.jaxrs.xml.elements.AllProp;
import net.java.dev.webdav.jaxrs.xml.elements.Collection;
import net.java.dev.webdav.jaxrs.xml.elements.Depth;
import net.java.dev.webdav.jaxrs.xml.elements.Error;
import net.java.dev.webdav.jaxrs.xml.elements.Exclusive;
import net.java.dev.webdav.jaxrs.xml.elements.HRef;
import net.java.dev.webdav.jaxrs.xml.elements.Include;
import net.java.dev.webdav.jaxrs.xml.elements.Location;
import net.java.dev.webdav.jaxrs.xml.elements.LockEntry;
import net.java.dev.webdav.jaxrs.xml.elements.LockInfo;
import net.java.dev.webdav.jaxrs.xml.elements.LockRoot;
import net.java.dev.webdav.jaxrs.xml.elements.LockScope;
import net.java.dev.webdav.jaxrs.xml.elements.LockToken;
import net.java.dev.webdav.jaxrs.xml.elements.LockType;
import net.java.dev.webdav.jaxrs.xml.elements.MultiStatus;
import net.java.dev.webdav.jaxrs.xml.elements.Owner;
import net.java.dev.webdav.jaxrs.xml.elements.Prop;
import net.java.dev.webdav.jaxrs.xml.elements.PropFind;
import net.java.dev.webdav.jaxrs.xml.elements.PropName;
import net.java.dev.webdav.jaxrs.xml.elements.PropStat;
import net.java.dev.webdav.jaxrs.xml.elements.PropertyUpdate;
import net.java.dev.webdav.jaxrs.xml.elements.Remove;
import net.java.dev.webdav.jaxrs.xml.elements.Response;
import net.java.dev.webdav.jaxrs.xml.elements.ResponseDescription;
import net.java.dev.webdav.jaxrs.xml.elements.Set;
import net.java.dev.webdav.jaxrs.xml.elements.Shared;
import net.java.dev.webdav.jaxrs.xml.elements.Status;
import net.java.dev.webdav.jaxrs.xml.elements.TimeOut;
import net.java.dev.webdav.jaxrs.xml.elements.Write;
import net.java.dev.webdav.jaxrs.xml.properties.CreationDate;
import net.java.dev.webdav.jaxrs.xml.properties.DisplayName;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLanguage;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentLength;
import net.java.dev.webdav.jaxrs.xml.properties.GetContentType;
import net.java.dev.webdav.jaxrs.xml.properties.GetETag;
import net.java.dev.webdav.jaxrs.xml.properties.GetLastModified;
import net.java.dev.webdav.jaxrs.xml.properties.LockDiscovery;
import net.java.dev.webdav.jaxrs.xml.properties.ResourceType;
import net.java.dev.webdav.jaxrs.xml.properties.SupportedLock;
import net.java.dev.webdav.util.Utilities;

/**
 * Provides support for custom extensions to WebDAV, like custom Properties and XML Elements.<br>
 * 
 * WebDAV allows custom extensions for XML Elements and Properties. To enable JAX-RS to deal with these, each of them must be implemented as a JAXB class and registered by passing it to the constructor of this factory.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#xml-extensibility">Chapter 17 "XML Extensibility in DAV" of RFC 2616 "Hypertext Transfer Protocol -- HTTP/1.1"</a>
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
	public static final JAXBContext build(final Class<?>... auxiliaryClasses) throws JAXBException {
		final Class<?>[] webDavClasses = new Class<?>[] { ActiveLock.class, AllProp.class, CannotModifyProtectedProperty.class, Collection.class,
				CreationDate.class, Depth.class, DisplayName.class, Error.class, Exclusive.class, GetContentLanguage.class, GetContentLength.class,
				GetContentType.class, GetETag.class, GetLastModified.class, HRef.class, Include.class, Location.class, LockDiscovery.class, LockEntry.class,
				LockInfo.class, LockRoot.class, LockScope.class, LockToken.class, LockTokenMatchesRequestUri.class, LockTokenSubmitted.class, LockType.class,
				MultiStatus.class, NoConflictingLock.class, NoExternalEntities.class, Owner.class, PreservedLiveProperties.class, Prop.class,
				PropertyUpdate.class, PropFind.class, PropFindFiniteDepth.class, PropName.class, PropStat.class, Remove.class, ResourceType.class,
				Response.class, ResponseDescription.class, Set.class, Shared.class, Status.class, SupportedLock.class, TimeOut.class, Write.class };
		final Class<?>[] allClasses = Utilities.append(webDavClasses, auxiliaryClasses);
		return JAXBContext.newInstance(allClasses);
	}
}
