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

package org.jugs.webdav.jaxrs;

import org.jugs.webdav.jaxrs.xml.conditions.*;
import org.jugs.webdav.jaxrs.xml.elements.Error;
import org.jugs.webdav.jaxrs.xml.elements.*;
import org.jugs.webdav.jaxrs.xml.properties.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.jugs.webdav.util.ElementOf.elementOf;

/**
 * Unit test for {@link WebDavJAXBContextBuilder}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class WebDavJAXBContextBuilderTest {
	private static JAXBContext context;

	@BeforeAll
	public static final void setUp() throws JAXBException {
		context = WebDavJAXBContextBuilder.build();
	}

	@Test
	public final void createsJAXBContext() {
		assertThat(context, instanceOf(JAXBContext.class));
	}

	@org.junit.jupiter.api.DisplayName("webDavClasses")
	@ParameterizedTest(name = "{index}) {0}")
	@ValueSource(classes = { ActiveLock.class, AllProp.class, CannotModifyProtectedProperty.class, Collection.class,
							 CreationDate.class, Depth.class, DisplayName.class, Error.class, Exclusive.class, GetContentLanguage.class, GetContentLength.class,
							 GetContentType.class, GetETag.class, GetLastModified.class, HRef.class, Include.class, Location.class, LockDiscovery.class, LockEntry.class,
							 LockInfo.class, LockRoot.class, LockScope.class, LockToken.class, LockTokenMatchesRequestUri.class, LockTokenSubmitted.class, LockType.class,
							 MultiStatus.class, NoConflictingLock.class, NoExternalEntities.class, Owner.class, PreservedLiveProperties.class, Prop.class, PropertyUpdate.class,
							 PropFind.class, PropFindFiniteDepth.class, PropName.class, PropStat.class, Remove.class, ResourceType.class, Response.class,
							 ResponseDescription.class, Set.class, Shared.class, Status.class, SupportedLock.class, TimeOut.class, Write.class })
	void containsWebDavClasses(Class<?> webDavElement) {
		assertThat(webDavElement, is(elementOf(context)));
	}

	@XmlRootElement
	private static final class CustomClass {
		// Intentionally left blank.
	}

	@Test
	public final void containsCustomClass() throws JAXBException {
		assertThat(CustomClass.class, is(elementOf(WebDavJAXBContextBuilder.build(CustomClass.class))));
	}

	@XmlRootElement
	private static final class UnboundClass {
		// Intentionally left blank.
	}

	@Test
	public final void doesNotContainUnboundClasses() {
		assertThat(UnboundClass.class, is(not(elementOf(context))));
	}
}
