/*
 * Copyright 2008, 2009 Markus KARG
 *
 * This file is part of webdav-jaxrs.
 *
 * webdav-jaxrs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * webdav-jaxrs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with webdav-jaxrs.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jugs.webdav.addressbook;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ApplicationAdapter;
import com.sun.jersey.api.core.ResourceConfig;

import java.io.IOException;

/**
 * Main class of JPA Address Book Sample.<br>
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
public final class Main {

	public static void main(final String[] args) throws IOException {
		AddressBookApplication app = new AddressBookApplication();
		ResourceConfig rc = new ApplicationAdapter(app);
		int port = 80;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		GrizzlyServerFactory.createHttpServer("http://localhost:" + port + "/", rc);
		System.out.println("Sample Server running... Kill process to stop.");
	}

}
