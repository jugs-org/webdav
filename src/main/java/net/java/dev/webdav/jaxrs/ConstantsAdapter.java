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
package net.java.dev.webdav.jaxrs;

import java.util.Collection;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Guarantees that any unmarshalled enum constants effectively are the constant Java instances itself, so that {@code ==} can be used for comparison.
 * 
 * Subclass must provide the constants to be used instead of an equal value by {@link #getConstants()}.
 * 
 * @since 1.2
 */
public abstract class ConstantsAdapter<T> extends XmlAdapter<T, T> {
	@Override
	public final T marshal(final T value) throws Exception {
		return value;
	}

	@SuppressWarnings("synthetic-access")
	@Override
	public final T unmarshal(final T value) throws Exception {
		return replaceValueByConstants(value, this.getConstants());
	}

	private static final <T> T replaceValueByConstants(final T value, final Collection<T> constants) {
		for (final T constant : constants)
			if (constant.equals(value))
				return constant;

		return value;
	}

	/**
	 * @return Constant instances to be returned by {@link #marshal(Object)} as a replacement for any equal instances. Must not be {@code null}.
	 */
	protected abstract Collection<T> getConstants();
}
