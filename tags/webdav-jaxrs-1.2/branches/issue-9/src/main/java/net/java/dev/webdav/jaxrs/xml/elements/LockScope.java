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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * WebDAV lockscope XML Element.
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_lockscope">Chapter 14.13 "lockscope XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(LockScope.LockScopeAdapter.class)
@XmlRootElement(name = "lockscope")
public enum LockScope {

	SHARED,

	EXCLUSIVE;

	/*
	 * XmlAdapter is intentionally not directly implemented by surrounding class
	 * to prevent third party code to call it's methods: Unfortunately
	 * XmlAdapter enforces public visibility of all it's e.
	 * 
	 * This inner class cannot be private since Sun's compiler doesn't allow
	 * that, while Eclipse's compiler actually does.
	 */
	protected static final class LockScopeAdapter extends XmlAdapter<LockScopeValueType, LockScope> {

		@Override
		public final LockScopeValueType marshal(final LockScope value) throws Exception {
			if (value == null)
				return null;

			switch (value) {
			case EXCLUSIVE:
				return LockScopeValueType.EXCLUSIVE;
			case SHARED:
				return LockScopeValueType.SHARED;
			default:
				return null;
			}
		}

		@Override
		public final LockScope unmarshal(final LockScopeValueType value) throws Exception {
			if (value == null)
				return null;

			if (value.exclusive != null)
				return EXCLUSIVE;

			if (value.shared != null)
				return SHARED;

			return null;
		}
	}

	@XmlAccessorType(FIELD)
	@XmlType(propOrder = { "exclusive", "shared" })
	private static final class LockScopeValueType {

		public static final LockScopeValueType SHARED = new LockScopeValueType(Shared.SINGLETON, null);

		public static final LockScopeValueType EXCLUSIVE = new LockScopeValueType(null, Exclusive.SINGLETON);

		// Protected instead of private to prevent synthetic accessor.
		protected Shared shared;

		// Protected instead of private to prevent synthetic accessor.
		protected Exclusive exclusive;

		// Singleton
		private LockScopeValueType() {
			// For unmarshalling only.
		}

		// Enum
		private LockScopeValueType(final Shared shared, final Exclusive exclusive) {
			this.shared = shared;
			this.exclusive = exclusive;
		}
	}
}
