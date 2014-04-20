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

import static java.util.Arrays.asList;
import static javax.ws.rs.Priorities.ENTITY_CODER;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * Unit test for {@link WebDAV}.
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class WebDAVTest {
	@Test
	public final void shouldBeProvider() {
		// given
		final Class<?> cls = WebDAV.class;

		// when

		// then
		assertThat(cls.isAnnotationPresent(Provider.class), is(true));
	}

	@Test
	public final void shouldHaveDefaultConstructor() throws InstantiationException, IllegalAccessException {
		// given
		final Class<?> cls = WebDAV.class;

		// when
		final Object instance = cls.newInstance();

		// then
		assertThat(instance, is(notNullValue()));
	}

	@Test
	public final void shouldImplementFeature() throws InstantiationException, IllegalAccessException {
		// given
		final Class<?> cls = WebDAV.class;

		// when
		final Object instance = cls.newInstance();

		// then
		assertThat(instance, is(instanceOf(Feature.class)));
	}

	@Test
	public final void shouldRegisterContextResolverAsEntityCoder() throws InstantiationException, IllegalAccessException {
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
	};

	@Test
	public final void shouldRegisterCustomExtensionsProvidedAsProperty() throws InstantiationException, IllegalAccessException {
		// given
		final WebDAV webDAV = new WebDAV();
		final Configuration configuration = mock(Configuration.class);
		when(configuration.getProperty(WebDAV.CUSTOM_EXTENSIONS)).thenReturn(asList(SomeCustomExtension.class));
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
