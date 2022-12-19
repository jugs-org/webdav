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
package org.jugs.webdav.fileserver;

import jakarta.ws.rs.core.Application;
import org.jugs.webdav.fileserver.resources.FileServerResource;
import org.jugs.webdav.jaxrs.xml.WebDavContextResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class FileServerApplication extends Application {
	public final static String RESOURCE_NAME = "fileserver";
	
	protected Set<Class<?>> entityClasses = new HashSet<Class<?>>();
	protected Set<Class<?>> serviceClasses = new HashSet<Class<?>>();

	private static final Logger logger = LoggerFactory.getLogger(FileServerApplication.class.getName());

	public FileServerApplication() {
		registerService(FileServerResource.class);
		logger.debug("FileServerApplication is instantiated.");
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
			logger.error("no singletons:", e);
			e.printStackTrace();
			System.out.println("Fuck!!!!!!");
			return null;
		}
	}
}
