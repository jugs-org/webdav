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

import org.jugs.webdav.jaxrs.xml.conditions.*;
import org.jugs.webdav.jaxrs.xml.elements.Error;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.jugs.webdav.util.ElementOf.elementOf;

/**
 * Unit test for {@link WebDavContextResolver}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class WebDavContextResolverTest {
	private static WebDavContextResolver resolver;

	@BeforeAll
	public static final void setUp() throws JAXBException {
		resolver = new WebDavContextResolver(CustomElement.class);
	}

	@Test
	public final void createsJAXBContext() {
		assertThat(resolver.getContext(MultiStatus.class), instanceOf(JAXBContext.class));
	}

	@ParameterizedTest
	@ValueSource(classes = { ActiveLock.class, AllProp.class, CannotModifyProtectedProperty.class, Collection.class,
							 CreationDate.class, Depth.class, DisplayName.class, Error.class, Exclusive.class, GetContentLanguage.class, GetContentLength.class,
							 GetContentType.class, GetETag.class, GetLastModified.class, HRef.class, Include.class, Location.class, LockDiscovery.class, LockEntry.class,
							 LockInfo.class, LockRoot.class, LockScope.class, LockToken.class, LockTokenMatchesRequestUri.class, LockTokenSubmitted.class, LockType.class,
							 MultiStatus.class, NoConflictingLock.class, NoExternalEntities.class, Owner.class, PreservedLiveProperties.class, Prop.class, PropertyUpdate.class,
							 PropFind.class, PropFindFiniteDepth.class, PropName.class, PropStat.class, Remove.class, ResourceType.class, Response.class,
							 ResponseDescription.class, Set.class, Shared.class, Status.class, SupportedLock.class, TimeOut.class, Write.class })
	public final void containsWebDavElements(final Class<?> webDavElement) {
		assertThat(webDavElement, is(elementOf(resolver.getContext(MultiStatus.class))));
	}

	@XmlRootElement
	private static class CustomElement {
		// Intentionally left blank.
	}

	@Test
	public final void containsCustomElements() throws JAXBException {
		assertThat(CustomElement.class, is(elementOf(resolver.getContext(MultiStatus.class))));
	}

	@XmlRootElement
	private static class UnboundElement {
		// Intentionally left blank.
	}

	@Test
	public final void doesNotContainUnboundElements() {
		assertThat(UnboundElement.class, is(not(elementOf(resolver.getContext(MultiStatus.class)))));
	}
}
