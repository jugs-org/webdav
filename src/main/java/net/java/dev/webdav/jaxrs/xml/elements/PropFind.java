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

package net.java.dev.webdav.jaxrs.xml.elements;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.java.dev.webdav.jaxrs.NullArgumentException;

/**
 * WebDAV propfind XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_propfind">Chapter 14.20 "propfind XML Element" of RFC 4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "propName", "allProp", "include", "prop" })
@XmlRootElement(name = "propfind")
public final class PropFind {

	@XmlElement(name = "propname")
	private PropName propName;

	@XmlElement(name = "allprop")
	private AllProp allProp;

	private Include include;

	private Prop prop;

	@SuppressWarnings("unused")
	private PropFind() {
		// For unmarshalling only.
	}

	public PropFind(final PropName propName) {
		if (propName == null)
			throw new NullArgumentException("propName");

		this.propName = propName;
	}

	public PropFind(final AllProp allProp, final Include include) {
		if (allProp == null)
			throw new NullArgumentException("allProp");

		this.allProp = allProp;
		this.include = include;
	}

	public PropFind(final AllProp allProp) {
		this(allProp, null);
	}

	public PropFind(final Prop prop) {
		if (prop == null)
			throw new NullArgumentException("prop");

		this.prop = prop;
	}

	public final PropName getPropName() {
		return this.propName;
	}

	public final AllProp getAllProp() {
		return this.allProp;
	}

	public final Include getInclude() {
		return this.include;
	}

	public final Prop getProp() {
		return this.prop;
	}

}
