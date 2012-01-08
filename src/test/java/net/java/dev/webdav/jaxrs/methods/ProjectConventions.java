/*
 * Copyright 2012 Markus KARG
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

package net.java.dev.webdav.jaxrs.methods;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.HttpMethod;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Tests WebDAV method annotations for JAX-RS compliance.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class ProjectConventions {
	@DataPoint
	public static Class<?> COPY = COPY.class;

	@DataPoint
	public static Class<?> LOCK = LOCK.class;

	@DataPoint
	public static Class<?> MKCOL = MKCOL.class;

	@DataPoint
	public static Class<?> MOVE = MOVE.class;

	@SuppressWarnings("deprecation")
	@DataPoint
	public static Class<?> OPTIONS = OPTIONS.class;

	@DataPoint
	public static Class<?> PROPFIND = PROPFIND.class;

	@DataPoint
	public static Class<?> PROPPATCH = PROPPATCH.class;

	@DataPoint
	public static Class<?> UNLOCK = UNLOCK.class;

	@Theory
	public final void annotationNameEqualsHttpMethodName(final Class<?> sample) {
		assertThat("HTTP method name must equal simple name of " + sample + ".", sample.getAnnotation(HttpMethod.class).value(),
				is(equalTo(sample.getSimpleName())));
	}
}
