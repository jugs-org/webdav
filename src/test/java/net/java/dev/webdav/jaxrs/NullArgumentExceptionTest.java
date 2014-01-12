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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Tests NullArgumentException.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class NullArgumentExceptionTest {
	@Test
	public final void extendsIllegalArgumentException() {
		assertThat(new NullArgumentException("x"), is(instanceOf(IllegalArgumentException.class)));
	}

	@Test
	public final void constructorProducesDesiredMessage() {
		assertThat(new NullArgumentException("x").getMessage(), is("Argument 'x' must not be null."));
	}
}
