package net.java.dev.webdav.jaxrs.methods;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.HttpMethod;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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
