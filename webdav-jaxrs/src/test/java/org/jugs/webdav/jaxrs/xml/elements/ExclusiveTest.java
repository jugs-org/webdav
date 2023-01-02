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

package org.jugs.webdav.jaxrs.xml.elements;

import org.jugs.webdav.jaxrs.AbstractJaxbCoreFunctionality;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;

/**
 * Unit test for {@link Exclusive}
 * 
 * @author Markus KARG (mkarg@java.net)
 */
public final class ExclusiveTest extends AbstractJaxbCoreFunctionality<Exclusive> {

	private static final Object[] SINGLETON = { Exclusive.EXCLUSIVE, "<D:exclusive xmlns:D=\"DAV:\"/>" };

	@Test
	void testMarshalling() throws JAXBException {
		marshalling(SINGLETON);
	}

	@Test
	void testUnmarshalling() throws JAXBException {
		unmarshalling(SINGLETON);
	}

	@Override
	protected Exclusive getSingleton() {
		return Exclusive.EXCLUSIVE;
	}

	@Override
	protected String getString() {
		return "Exclusive[]";
	}

}
