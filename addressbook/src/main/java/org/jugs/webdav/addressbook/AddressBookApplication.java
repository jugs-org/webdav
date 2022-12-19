/*
 * Copyright 2008, 2009 Markus KARG
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

package org.jugs.webdav.addressbook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;

import jakarta.ws.rs.core.Application;
import jakarta.xml.bind.JAXBException;

import org.jugs.webdav.jaxrs.xml.WebDavContextResolver;
import org.slf4j.LoggerFactory;

/**
 * Sole JAX-RS Application of JPA Address Book Sample.
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
public final class AddressBookApplication extends Application {

	private static final Logger logger = LoggerFactory.getLogger(AddressBookApplication.class);

	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<>(Arrays.asList(AddressBook.class, MicrosoftRedirectorPatch1.class));
	}

	@Override
	public Set<Object> getSingletons() {
		try {
			return new HashSet<>(
					Arrays.asList(new WebDavContextResolver(MicrosoftRedirectorPatch2.class, Win32LastAccessTime.class),
							new PersistenceExceptionMapper()));
		} catch (final JAXBException e) {
			logger.error("No singletons:", e);
			return null;
		}
	}

}
