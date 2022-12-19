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
package org.jugs.webdav.fileserver.tools;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.core.Response.Status;

import org.jugs.webdav.jaxrs.xml.elements.Prop;
import org.jugs.webdav.jaxrs.xml.elements.PropStat;
import org.jugs.webdav.jaxrs.xml.properties.CreationDate;
import org.jugs.webdav.jaxrs.xml.properties.DisplayName;
import org.jugs.webdav.jaxrs.xml.properties.GetContentLength;
import org.jugs.webdav.jaxrs.xml.properties.GetContentType;
import org.jugs.webdav.jaxrs.xml.properties.GetLastModified;
import org.jugs.webdav.jaxrs.xml.properties.ResourceType;

import org.w3c.dom.Element;


public class PropStatBuilder {
	private Prop properties;
	private Status status;
	private Set<String> names;

	public PropStatBuilder() {
		properties = new Prop();
		names = new HashSet<String>();
	}
	
	public PropStatBuilder creationDate(Date dateTime){
		if(!names.contains("creationdate")){
			CreationDate create = new CreationDate(dateTime);
			properties.getProperties().add(create);
			names.add("creationdate");
		}
		
		return this;
	}
	
	public PropStatBuilder lastModified(Date dateTime){
		if(!names.contains("getlastmodified")){
			GetLastModified lastModified = new GetLastModified(dateTime);
			properties.getProperties().add(lastModified);
			names.add("getlastmodified");
		}
		
		return this;
	}
	
	public PropStatBuilder contentLength(long length){
		if(!names.contains("contentlength")){
			GetContentLength contentLength = new GetContentLength(length);
			properties.getProperties().add(contentLength);
			names.add("contentlength");
		}
	
		return this;
	}

	public PropStatBuilder isResource(String mime){
		if(!names.contains("resourcetype")){
			GetContentType type = new GetContentType(mime);
			properties.getProperties().add(type);
			names.add("resourcetype");
		}
		
		return this;
	}
	
	public PropStatBuilder isCollection(){
		if(!names.contains("resourcetype")){
			ResourceType type = ResourceType.COLLECTION;
			properties.getProperties().add(type);
			names.add("resourcetype");
		}
		
		return this;
	}
	
	public PropStatBuilder displayName(String displayName){
		if(!names.contains("displayname")){
			DisplayName name = new DisplayName(displayName);
			properties.getProperties().add(name);
			names.add("displayname");
		}
		
		return this;
	}
	
	public PropStat notFound(Prop allprops){
		Prop notFound = new Prop();
		for(Object prop : allprops.getProperties()){
			if(prop instanceof Element){
				Element element = (Element)prop;
				String name = element.getLocalName();
				System.out.println(name);
				if(!names.contains(name)){
					notFound.getProperties().add(prop);
				}
			}	
		}
		
		PropStat stat = new PropStat(notFound, new org.jugs.webdav.jaxrs.xml.elements.Status(Status.NOT_FOUND));
		
		return stat;
	}
	
	public PropStatBuilder status(Status status){
		this.status = status;
		
		return this;
	}
	
	public PropStat build(){
		PropStat stat = new PropStat(properties, new org.jugs.webdav.jaxrs.xml.elements.Status(status));
		
		return stat;
	}
}
