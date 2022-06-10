/* Copyright 2008, 2009 Daniel MANZKE
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

/**
 * HttpMethod is for internal use only. 
 * 
 * This enumeration lists all Method which has to processed by the Interop Filters and Adapters..
 * 
 * @author Daniel Manzke
 */
public enum HttpMethod{
	OPTIONS,
	PROPFIND,
	PROPPATCH,
	UNKOWN;

	public static HttpMethod method(String method){
		if(method.contains("OPTIONS")){
			return OPTIONS;
		}
		if(method.contains("PROPFIND")){
			return PROPFIND;
		}
		if(method.contains("PROPPATCH")){
			return PROPPATCH;
		}
		
		return UNKOWN;
	}
}

