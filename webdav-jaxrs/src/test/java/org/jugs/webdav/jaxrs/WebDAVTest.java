/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2025 Java User Group Stuttgart
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

package org.jugs.webdav.jaxrs;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static jakarta.ws.rs.Priorities.ENTITY_CODER;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link WebDAV}.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class WebDAVTest {
	@Test
	public void shouldBeProvider() {
		// given
		final Class<?> cls = WebDAV.class;

		// when

		// then
		assertThat(cls.isAnnotationPresent(Provider.class), is(true));
	}

	@Test
	public void shouldHaveDefaultConstructor() throws ReflectiveOperationException {
		// given
		final Class<?> cls = WebDAV.class;

		// when
		final Object instance = cls.getDeclaredConstructor().newInstance();

		// then
		assertThat(instance, is(notNullValue()));
	}

	@Test
	public void shouldImplementFeature() throws ReflectiveOperationException {
		// given
		final Class<?> cls = WebDAV.class;

		// when
		final Object instance = cls.getDeclaredConstructor().newInstance();

		// then
		assertThat(instance, is(instanceOf(Feature.class)));
	}

	@Test
	public void shouldRegisterContextResolverAsEntityCoder() {
		// given
		final WebDAV webDAV = new WebDAV();
		final FeatureContext context = mock(FeatureContext.class);
		when(context.getConfiguration()).thenReturn(mock(Configuration.class));

		// when
		final boolean isEnabled = webDAV.configure(context);

		// then
		assertThat(isEnabled, is(true));
		verify(context).register(any(WebDavContextResolver.class), eq(ENTITY_CODER));
	}

	@XmlRootElement
	private static final class SomeCustomExtension {
	}

	@Test
	public void shouldRegisterCustomExtensionsProvidedAsProperty() {
		// given
		final WebDAV webDAV = new WebDAV();
		final Configuration configuration = mock(Configuration.class);
		when(configuration.getProperty(WebDAV.CUSTOM_EXTENSIONS)).thenReturn(List.of(SomeCustomExtension.class));
		final FeatureContext context = mock(FeatureContext.class);
		when(context.getConfiguration()).thenReturn(configuration);

		// when
		webDAV.configure(context);

		// then
		final ArgumentCaptor<WebDavContextResolver> argument = ArgumentCaptor.forClass(WebDavContextResolver.class);
		verify(context).register(argument.capture(), eq(ENTITY_CODER));
		assertThat(argument.getValue().getContext(SomeCustomExtension.class), is(notNullValue()));
	}
}
