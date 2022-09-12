/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2022 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs.methods;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.HttpMethod;

import org.jugs.webdav.jaxrs.methods.*;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Tests WebDAV method annotations for JAX-RS compliance.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
@RunWith(Theories.class)
public final class JaxRsComplianceTest {
	@SuppressWarnings("deprecation")
	@DataPoints
	public static final Class<?>[] DATA_POINTS = {COPY.class, LOCK.class, MKCOL.class, MOVE.class, OPTIONS.class, PROPFIND.class, PROPPATCH.class,
												  UNLOCK.class };

	@Theory
	public final void isAnAnnotation(final Class<?> dataPoint) {
		assertThat(dataPoint + " must be an annotation.", dataPoint.isAnnotation(), is(true));
	}

	@Theory
	public final void canBeUsedOnMethods(final Class<?> dataPoint) {
		assertThat(dataPoint + " must be annotated with @Target.", dataPoint.isAnnotationPresent(Target.class), is(true));
		assertThat("Target element type of " + dataPoint + " must be METHOD.", dataPoint.getAnnotation(Target.class).value()[0], is(sameInstance(METHOD)));
	}

	@Theory
	public final void hasRuntimeRetention(final Class<?> dataPoint) {
		assertThat(dataPoint + " must be annotated with @Retention.", dataPoint.isAnnotationPresent(Retention.class), is(true));
		assertThat("Retention policy of " + dataPoint + " must be RUNTIME.", dataPoint.getAnnotation(Retention.class).value(), is(sameInstance(RUNTIME)));
	}

	@Theory
	public final void hasHttpMethod(final Class<?> dataPoint) {
		assertThat(dataPoint + " must be annotated with @HttpMethod.", dataPoint.isAnnotationPresent(HttpMethod.class), is(true));
	}
}
