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
		assertThat("Must be an annotation.", sample.isAnnotation(), is(true));
	}

	@Theory
	public final void canBeUsedOnMethods(final Class<?> sample) {
		assertThat("Must have @Target.", sample.isAnnotationPresent(Target.class), is(true));
		assertThat("Target element type must be METHOD.", sample.getAnnotation(Target.class).value()[0], is(sameInstance(METHOD)));
	}

	@Theory
	public final void hasRuntimeRetention(final Class<?> sample) {
		assertThat("Must have @Retention.", sample.isAnnotationPresent(Retention.class), is(true));
		assertThat("Retention policy must be RUNTIME.", sample.getAnnotation(Retention.class).value(), is(sameInstance(RUNTIME)));
	}

	@Theory
	public final void hasHttpMethod(final Class<?> sample) {
		assertThat("Must have @HttpMethod.", sample.isAnnotationPresent(HttpMethod.class), is(true));
	}
}
