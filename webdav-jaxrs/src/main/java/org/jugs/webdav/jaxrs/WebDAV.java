/*
 * #%L
 * WebDAV Support for JAX-RS
 * %%
 * Copyright (C) 2008 - 2023 Java User Group Stuttgart
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

import static javax.ws.rs.Priorities.ENTITY_CODER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;

/**
 * Provides WebDAV support to JAX-RS applications.
 * 
 * @author Markus KARG (mkarg@java.net)
 * 
 * @since 2.0
 */
@Provider
public final class WebDAV implements Feature {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebDAV.class);

	/**
	 * To register WebDAV custom extensions (e. g. to support vendor specific properties) a {@code List<Class<?>>} can be provided by this configuration
	 * property. Each of these classes has to be a JAXB element.
	 */
	public static final String CUSTOM_EXTENSIONS = WebDAV.class.getName() + ".CUSTOM_EXTENSIONS";

	@Override
	public boolean configure(final FeatureContext context) {
		try {
			final Collection<Class<?>> customExtensionClasses = buildCustomExtensionClasses(context.getConfiguration().getProperty(CUSTOM_EXTENSIONS));
			if (customExtensionClasses.isEmpty())
				LOGGER.debug("Using no WebDAV custom extension classes");
			else
				for (final Class<?> customExtensionClass : customExtensionClasses)
					LOGGER.debug("Using WebDAV custom extension class " + customExtensionClass);
			context.register(new WebDavContextResolver(customExtensionClasses.toArray(new Class<?>[customExtensionClasses.size()])), ENTITY_CODER);
			LOGGER.info("Enabled WebDAV Support for JAX-RS");
			return true;
		} catch (final JAXBException e) {
			LOGGER.error("Disabled WebDAV Support for JAX-RS due to configuration failure: ", e);
			return false;
		}
	}

	private Collection<Class<?>> buildCustomExtensionClasses(final Object customExtensionsProperty) {
		if (customExtensionsProperty == null)
			return Collections.emptyList();

		if (!(customExtensionsProperty instanceof Collection<?>)) {
			LOGGER.warn("Ignoring WebDAV custom extensions since provided property value is not of expected type Collection<Class<?>>: "
					+ customExtensionsProperty);
			return Collections.emptyList();
		}

		final Collection<?> collection = (Collection<?>) customExtensionsProperty;
		final Collection<Class<?>> customExtensionClasses = new ArrayList<>(collection.size());
		for (final Object object : collection)
			if (object instanceof Class<?>)
				customExtensionClasses.add((Class<?>) object);
			else
				LOGGER.warn("Ignoring WebDAV custom extension since provided property value is not of expected type Class<?>: " + object);

		return customExtensionClasses;
	}
}
