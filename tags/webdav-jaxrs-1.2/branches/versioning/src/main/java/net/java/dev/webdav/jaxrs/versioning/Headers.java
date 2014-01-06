/*
 * Copyright 2009 Markus KARG
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

package net.java.dev.webdav.jaxrs.versioning;

/**
 * Versioning Extensions to WebDAV Headers
 * 
 * @author Markus KARG (mkarg@users.dev.java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc3253.html">RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
 */
public interface Headers {

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "version-control"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.3">Chapter 3. "Version-Control Feature" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_VERSION_CONTROL = "version-control";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "checkout-in-place"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.4.6">Chapter 4.6 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_CHECKOUT_IN_PLACE = "checkout-in-place";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "version-history"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.5.5">Chapter 5.5 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_VERSION_HISTORY = "version-history";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "workspace"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.6.4">Chapter 6.4 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_WORKSPACE = "workspace";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "update"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.7.2">Chapter 7.2 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_UPDATE = "update";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "label"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.8.4">Chapter 8.4 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_LABEL = "label";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "working-resource"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.9.5">Chapter 9.5 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_WORKING_RESOURCE = "working-resource";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "merge"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.11.4">Chapter 11.4 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_MERGE = "merge";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "baseline"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.12.8">Chapter 12.8 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_BASELINE = "baseline";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "activity"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.13.7">Chapter 13.7 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_ACTIVITY = "activity";

	/**
	 * Versioning Extensions to WebDAV "DAV" Header Value "version-controlled-collection"
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#rfc.section.13.7">Chapter 13.7 "Additional OPTIONS Semantics" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String DAV_VERSION_CONTROLLED_COLLECTION = "version-controlled-collection";

	/**
	 * Versioning Extensions to WebDAV "Label" Header
	 * 
	 * @see <a href="http://www.webdav.org/specs/rfc3253.html#label.header">Chapter 8.3 "Label Header" of RFC 3253 "Versioning Extensions to WebDAV (Web Distributed Authoring and Versioning)"</a>
	 */
	public static final String LABEL = "Label";

}
