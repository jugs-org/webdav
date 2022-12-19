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

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Main class of JPA Address Book Sample.<br>
 * 
 * @author Markus KARG (mkarg@dev.users.java.net)
 */
public final class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	private static HttpServer server;

	public static void main(final String[] args) throws IOException {
		start(args);
		System.out.println(server + " started, press <return> to stop...");
		System.in.read();
	}

	public static void start(final String[] args) {
		AddressBookApplication app = new AddressBookApplication();
		ResourceConfig rc = ResourceConfig.forApplication(app);
		URI serverURI = URI.create("http://localhost/");
		if (args.length > 0) {
			serverURI = URI.create("http://localhost:" + args[0].trim() + "/");
		}
		server = GrizzlyHttpServerFactory.createHttpServer(serverURI, rc);
		log.info("{} started ({}).", server, serverURI);
	}

	public static void stop() {
		server.stop();
		log.info("{} stopped.", server);
	}

}
