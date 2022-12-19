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

package org.jugs.webdav.jaxrs.xml.conditions;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

/**
 * Unit test for {@link PreservedLiveProperties}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class PreservedLivePropertiesTest extends AbstractJaxbCoreFunctionality<PreservedLiveProperties> {

	private static final Object[] SINGLETON = new Object[] { PreservedLiveProperties.PRESERVED_LIVE_PROPERTIES,
			"<D:preserved-live-properties xmlns:D=\"DAV:\"/>" };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(SINGLETON);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(SINGLETON);
	}

	@Override
	protected PreservedLiveProperties getSingleton() {
		return PreservedLiveProperties.PRESERVED_LIVE_PROPERTIES;
	}

	@Override
	protected String getString() {
		return "PreservedLiveProperties[]";
	}

}
