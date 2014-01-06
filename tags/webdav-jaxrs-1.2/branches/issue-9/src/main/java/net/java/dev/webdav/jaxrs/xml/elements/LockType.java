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

package net.java.dev.webdav.jaxrs.xml.elements;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * WebDAV locktype XML Element.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_locktype">Chapter 14.15 "locktype XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(LockType.LockTypeAdapter.class)
@XmlRootElement(name = "locktype")
public enum LockType {

	WRITE;

	/*
	 * XmlAdapter is intentionally not directly implemented by surrounding class
	 * to prevent third party code to call it's methods: Unfortunately
	 * XmlAdapter enforces public visibility of all it's e.
	 * 
	 * This inner class cannot be private since Sun's compiler doesn't allow
	 * that, while Eclipse's compiler actually does.
	 */
	protected static final class LockTypeAdapter extends XmlAdapter<LockTypeValueType, LockType> {

		@Override
		public final LockTypeValueType marshal(final LockType value) throws Exception {
			if (value == null)
				return null;
			
			switch (value) {
			case WRITE:
				return LockTypeValueType.WRITE;
			default:
				return null;
			}
		}

		@Override
		public final LockType unmarshal(final LockTypeValueType value) throws Exception {
			if (value == null)
				return null;
			
			if (value.write != null)
				return WRITE;

			return null;
		}
	}

	@XmlAccessorType(FIELD)
	private static final class LockTypeValueType {

		public static final LockTypeValueType WRITE = new LockTypeValueType(Write.SINGLETON);

		// Protected instead of private to prevent synthetic accessor.
		protected Write write;

		// Singleton
		private LockTypeValueType() {
			// For unmarshalling only.
		}

		// Enum
		private LockTypeValueType(final Write write) {
			this.write = write;
		}

	}
}
