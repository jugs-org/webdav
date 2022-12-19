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

/**
 * WebDAV Extension property which is working around a bug in the Microsoft
 * WebDAV-Mini-Redirector.<br>
 * 
 * This particular client software expects XML namespace prefixes being used on
 * each single XML element, while JABX 2.0 is using namespace prefixes only if
 * more than one namespace is used. To workaround this issue, this
 * "fake property" is sent always, which uses the default namespace -- and such
 * all other elements are forced to contain their namespace prefix.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 */
@XmlRootElement
public final class MicrosoftRedirectorPatch2 {
	// Has no elements.
}
