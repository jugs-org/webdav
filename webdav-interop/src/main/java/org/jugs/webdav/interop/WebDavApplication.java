/*
 * Copyright 2008, 2009 Daniel MANZKE
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
package org.jugs.webdav.interop;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Application;
import javax.xml.bind.JAXBException;

import org.jugs.webdav.jaxrs.xml.WebDavContextResolver;



public class WebDavApplication extends Application {
	protected Set<Class<?>> entityClasses = new HashSet<Class<?>>();
	protected Set<Class<?>> serviceClasses = new HashSet<Class<?>>();

	public WebDavApplication(Class<?>... serviceClasses) {
		registerService(serviceClasses);
		registerService(WindowsRedirectorPatchResource.class);
		registerEntity(WindowsRedirectorPatchProperty.class);
	}
	
	public void registerEntity(Class<?>... additionalClasses){
		entityClasses.addAll(Arrays.asList(additionalClasses));
	}
	
	public void registerService(Class<?>... additionalClasses){
		serviceClasses.addAll(Arrays.asList(additionalClasses));
	}

	@Override
	public Set<Class<?>> getClasses() {
		return serviceClasses;
	}
	
	@Override
	public Set<Object> getSingletons() {
		try {
			return new HashSet<Object>(Arrays.asList(new WebDavContextResolver(entityClasses.toArray(new Class[entityClasses.size()]))));
		} catch (final JAXBException e) {
			Logger.getLogger(WebDavApplication.class.getName()).severe(e.toString());
			return null;
		}
	}
}
