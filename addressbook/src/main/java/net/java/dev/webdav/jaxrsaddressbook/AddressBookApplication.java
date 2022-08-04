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

package net.java.dev.webdav.jaxrsaddressbook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;
import javax.xml.bind.JAXBException;

import net.java.dev.webdav.jaxrs.xml.WebDavContextResolver;

/**
 * Sole JAX-RS Application of JPA Address Book Sample.
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
public final class AddressBookApplication extends Application {

	@SuppressWarnings("unchecked")
	@Override
	public final Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(Arrays.asList(AddressBook.class, MicrosoftRedirectorPatch1.class));
	}

	@Override
	public Set<Object> getSingletons() {
		try {
			return new HashSet<Object>(Arrays.asList(new WebDavContextResolver(MicrosoftRedirectorPatch2.class, Win32LastAccessTime.class),
					new PersistenceExceptionMapper()));
		} catch (final JAXBException e) {
			Logger.getLogger(AddressBookApplication.class.getName()).severe(e.toString());
			return null;
		}
	}

}
