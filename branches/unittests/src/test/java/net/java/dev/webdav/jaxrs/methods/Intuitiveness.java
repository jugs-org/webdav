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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Tests WebDAV method annotations's intuitiveness (i. e. whether the API is intuitive to use).
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class Intuitiveness {
	@SuppressWarnings("deprecation")
	@DataPoints
	public static final Class<?>[] DATA_POINTS = { COPY.class, LOCK.class, MKCOL.class, MOVE.class, OPTIONS.class, PROPFIND.class, PROPPATCH.class, UNLOCK.class };

	@Theory
	public final void annotationNameEqualsHttpMethodName(final Class<?> dataPoint) {
		assertThat("HTTP method name must equal simple name of " + dataPoint + ".", dataPoint.getAnnotation(HttpMethod.class).value(),
				is(equalTo(dataPoint.getSimpleName())));
	}
}
