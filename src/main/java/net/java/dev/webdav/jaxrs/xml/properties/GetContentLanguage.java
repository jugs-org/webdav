/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2014 The java.net WebDAV Project
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

import static java.util.Collections.singleton;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.java.dev.webdav.jaxrs.ConstantsAdapter;
import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV getcontentlanguage Property.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#PROPERTY_getcontentlanguage">Chapter 15.3 "getcontentlanguage Property" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlJavaTypeAdapter(GetContentLanguage.Adapter.class)
@XmlRootElement(name = "getcontentlanguage")
public final class GetContentLanguage {
	/**
	 * Singleton empty instance for use as property name only, providing improved performance and the ability to compare by <em>same</em> instance.
	 * 
	 * @since 1.2
	 */
	public static final GetContentLanguage GETCONTENTLANGUAGE = new GetContentLanguage();

	@XmlValue
	private final String languageTag;

	/**
	 * @deprecated Since 1.2. Use {@link #GETCONTENTLANGUAGE} instead to obtain a singleton empty instance. In future releases this will have {@code private}
	 *             visibility.
	 */
	@Deprecated
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

	/**
	 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used form comparison.
	 * 
	 * @since 1.2
	 */
	protected static final class Adapter extends ConstantsAdapter<GetContentLanguage> {
		@Override
		protected final Collection<GetContentLanguage> getConstants() {
			return singleton(GETCONTENTLANGUAGE);
		}
	}
}
