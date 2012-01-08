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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
public final class JaxRsCompliance {
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
	public final void isAnAnnotation(final Class<?> sample) {
		assertThat(sample + " must be an annotation.", sample.isAnnotation(), is(true));
	}

	@Theory
	public final void canBeUsedOnMethods(final Class<?> sample) {
		assertThat(sample + " must be annotated with @Target.", sample.isAnnotationPresent(Target.class), is(true));
		assertThat("Target element type of " + sample + " must be METHOD.", sample.getAnnotation(Target.class).value()[0], is(sameInstance(METHOD)));
	}

	@Theory
	public final void hasRuntimeRetention(final Class<?> sample) {
		assertThat(sample + " must be annotated with @Retention.", sample.isAnnotationPresent(Retention.class), is(true));
		assertThat("Retention policy of " + sample + " must be RUNTIME.", sample.getAnnotation(Retention.class).value(), is(sameInstance(RUNTIME)));
	}

	@Theory
	public final void hasHttpMethod(final Class<?> sample) {
		assertThat(sample + " must be annotated with @HttpMethod.", sample.isAnnotationPresent(HttpMethod.class), is(true));
	}
}
