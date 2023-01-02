/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.xml.elements;

import static java.util.Objects.hash;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import static org.jugs.webdav.util.Utilities.array;
import static org.jugs.webdav.util.Utilities.notNull;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jugs.webdav.util.Utilities;

/**
 * WebDAV propfind XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#ELEMENT_propfind">Chapter 14.20 "propfind XML Element" of RFC 4918
 *      "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlAccessorType(FIELD)
@XmlType(propOrder = { "propName", "allProp", "include", "prop" })
@XmlRootElement(name = "propfind")
public final class PropFind {

	@XmlElement(name = "propname")
	private final PropName propName;

	@XmlElement(name = "allprop")
	private final AllProp allProp;

	private final Include include;

	private final Prop prop;

	@SuppressWarnings("unused")
	private PropFind() {
		this(null, null, null, null);
	}

	private PropFind(final PropName propName, final AllProp allProp, final Include include, final Prop prop) {
		this.propName = propName;
		this.allProp = allProp;
		this.include = include;
		this.prop = prop;

	}

	public PropFind(final PropName propName) {
		this(notNull(propName, "propName"), null, null, null);
	}

	public PropFind(final AllProp allProp, final Include include) {
		this(null, notNull(allProp, "allProp"), include, null);
	}

	public PropFind(final AllProp allProp) {
		this(null, notNull(allProp, "allProp"), null, null);
	}

	public PropFind(final Prop prop) {
		this(null, null, null, notNull(prop, "prop"));
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

	@Override
	public final boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof PropFind))
			return false;

		final PropFind that = (PropFind) o;

		return Arrays.equals(array(this.propName, this.allProp, this.include, this.prop), array(that.propName, that.allProp, that.include, that.prop));
	}

	@Override
	public final int hashCode() {
		return hash(this.propName, this.allProp, this.include, this.prop);
	}

	@Override
	public final String toString() {
		return Utilities.toString(this, this.propName, this.allProp, this.include, this.prop);
	}
}
