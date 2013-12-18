/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2013 The java.net WebDAV Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package net.java.dev.webdav.jaxrs.xml.properties;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV getcontentlanguage Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontentlanguage">Chapter 15.3 "getcontentlanguage Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "getcontentlanguage")
public final class GetContentLanguage {

	@XmlValue
	private final String languageTag;

	/**
	 * Creates an empty (thus <em>invalid</em>) instance. Use <em>only</em> to list property name within response to &lt;propname/&gt; request. Not to be used
	 * for creation of valid instances of this property; use {@link #GetContentLanguage(String)} instead.
	 */
	public GetContentLanguage() {
		this.languageTag = "";
	}

	public GetContentLanguage(final String languageTag) {
		if (languageTag == null)
			throw new NullArgumentException("languageTag");

		this.languageTag = languageTag;
	}

	public final String getLanguageTag() {
		return this.languageTag;
	}

	@Override
	public final boolean equals(final Object other) {
		if (!(other instanceof GetContentLanguage))
			return false;

		final GetContentLanguage that = (GetContentLanguage) other;

		return this.languageTag.equals(that.languageTag);
	}

	@Override
	public final int hashCode() {
		return this.languageTag.hashCode();
	}
}
