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

package net.java.dev.webdav.jaxrs.xml.conditions;

import static java.util.Collections.unmodifiableList;
import static net.java.dev.webdav.util.Utilities.append;
import static net.java.dev.webdav.util.Utilities.notNull;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.java.dev.webdav.jaxrs.xml.elements.HRef;

/**
 * WebDAV lock-token-submitted Precondition XML Element.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @see <a href="http://www.webdav.org/specs/rfc4918.html#precondition.postcondition.xml.elements">Chapter 16 "Precondition/Postcondition XML Elements" of RFC
 *      4918 "HTTP Extensions for Web Distributed Authoring and Versioning (WebDAV)"</a>
 */
@XmlRootElement(name = "lock-token-submitted")
public final class LockTokenSubmitted {

	@XmlElement(name = "href")
	private final List<HRef> hRefs;

	@SuppressWarnings("unused")
	private LockTokenSubmitted() {
		this.hRefs = new LinkedList<HRef>();
	}

	public LockTokenSubmitted(final HRef hRef, final HRef... hRefs) {
		this.hRefs = append(notNull(hRef, "hRef"), hRefs);
	}

	public final List<HRef> getHRefs() {
		return unmodifiableList(this.hRefs);
	}

	@Override
	public final boolean equals(final Object other) {
		if (other == this)
			return true;

		if (!(other instanceof LockTokenSubmitted))
			return false;

		final LockTokenSubmitted that = (LockTokenSubmitted) other;

		return this.hRefs.equals(that.hRefs);
	}

	@Override
	public final int hashCode() {
		return this.hRefs.hashCode();
	}
}
