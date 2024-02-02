/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2024 Java User Group Stuttgart
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

import static jakarta.ws.rs.core.MediaType.APPLICATION_XML;
import static jakarta.ws.rs.core.MediaType.TEXT_XML;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBIntrospector;

import org.jugs.webdav.util.Utilities;

/**
 * Provides support for custom extensions to WebDAV, like custom properties and XML elements.<br>
 * 
 * WebDAV allows custom extensions for XML elements and properties. To enable JAX-RS to deal with these, each of them must be implemented as a JAXB class and
 * registered by passing it to the constructor of this resolver.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#xml-extensibility">Chapter 17 "XML Extensibility in DAV" of RFC 2616
 *      "Hypertext Transfer Protocol -- HTTP/1.1"</a>
 * 
 * @since 2.0
 */
@Produces({ APPLICATION_XML, TEXT_XML })
final class WebDavContextResolver implements ContextResolver<JAXBContext> {
	private final JAXBContext context;

	private final JAXBIntrospector introspector;

	/**
	 * Creates an instance of this resolver, registering the provided custom XML Elements and Properties.
	 * 
	 * @param additionalClasses
	 *            The custom extensions (JAXB classes) to be registered (can be left blank).
	 * @throws JAXBException
	 *             if an error was encountered while creating the JAXBContext, such as (but not limited to): No JAXB implementation was discovered, Classes use
	 *             JAXB annotations incorrectly, Classes have colliding annotations (i.e., two classes with the same type name), The JAXB implementation was
	 *             unable to locate provider-specific out-of-band information (such as additional files generated at the development time.)
	 */
	public WebDavContextResolver(final Class<?>... additionalClasses) throws JAXBException {
		this.context = WebDavJAXBContextBuilder.build(additionalClasses);
		this.introspector = this.context.createJAXBIntrospector();
	}

	/**
	 * @return A single, shared context for both, WebDAV XML Elements and Properties and custom extensions.
	 */
	@Override
	public final JAXBContext getContext(final Class<?> cls) {
		return this.introspector.isElement(Utilities.buildInstanceOf(cls)) ? this.context : null;
	}
}
