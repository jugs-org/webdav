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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Microsoft WebDAV Extension property "Win32LastAccessTime".
 * 
 * This class serves as a sample for processing custom extension attributes.
 * 
 * The "Win32LastAccessTime" dead property is sent as part of a PROPPATCH request by
 * the Microsoft WebDAV-Mini-Redirector.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
@XmlRootElement(name = "Win32LastAccessTime", namespace = "urn:schemas-microsoft-com:")
public final class Win32LastAccessTime {

	@XmlValue
	private String content;

	/**
	 * @return Client specific string which is not to be further parsed, according to Microsoft's documentation.
	 * @see <a href="http://msdn.microsoft.com/en-us/library/cc250144(PROT.10).aspx">Chapter 2.2.10.8 "Z:Win32LastAccessTime Property" of MS-WDVME "Web Distributed Authoring and Versioning (WebDAV) Protocol: Microsoft Extensions"</a>
	 */
	public final String getContent() {
		return this.content;
	}

	/**
	 * @return "Win32LastAccesstime (content)"
	 */
	@Override
	public final String toString() {
		return String.format("Win32LastAccessTime (%s)", this.content);
	}

}
