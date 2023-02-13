/*
 * Copyright 2008, 2009 Daniel MANZKE
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
package org.jugs.webdav.fileserver;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.jugs.webdav.fileserver.tools.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

public final class FileServerStarter {

	private static final Logger log = LoggerFactory.getLogger(FileServerStarter.class);
	private static HttpServer server;

	/**
	 * Starts the FileServer application.
	 *
	 * @param args if given the first arg is the port
	 * @throws IOException in case of problems
	 */
	public static void main(final String[] args) throws IOException {
		start(args);
		System.out.println(server + " started, press <return> to stop...");
		System.in.read();
	}

	public static void start(String[] args) {
		FileServerApplication app = new FileServerApplication();

		Client client = createClient();
		log.info("{} created.", client);

		// create a resource config that scans for JAX-RS resources and providers
		ResourceConfig rc = ResourceConfig.forApplication(app);
		URI serverURI = URI.create("http://localhost/");
		if (args.length > 0) {
			serverURI = URI.create("http://localhost:" + args[0].trim() + "/");
		}
		log.info("Running {}...", serverURI);
		server = GrizzlyHttpServerFactory.createHttpServer(serverURI, rc);
		log.info("{} started ({}).", server, serverURI);
		addLoggingFilter();
	}

	private static void addLoggingFilter() {
		NetworkListener listener = server.getListener("grizzly");
		FilterChain filterChain = listener.getFilterChain();
		filterChain.add(new LoggingFilter());
	}

	public static void stop() {
		server.shutdown();
		log.info("{} stopped.", server);
	}

	private static Client createClient() {
		ClientConfig config = new ClientConfig();
		//config.register(LoggingFilter.class);
		return ClientBuilder.newClient(config);
	}

}
