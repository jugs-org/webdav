/* Copyright 2008, 2009 Daniel MANZKE
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
package org.jugs.webdav.interop.grizzly;

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;

import java.io.CharArrayWriter;
import java.io.StringReader;
import java.util.logging.Level;
import org.slf4j.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jugs.webdav.interop.HttpMethod;

import com.sun.grizzly.tcp.Adapter;
import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.tcp.Response;
import com.sun.grizzly.tcp.ResponseFilter;
import com.sun.grizzly.util.buf.ByteChunk;
import org.slf4j.LoggerFactory;

public class WindowsRedirectorPatchResourceAdapter implements Adapter {
	private static final Logger logger = LoggerFactory.getLogger(WindowsRedirectorPatchResourceAdapter.class);
	private Adapter original;
	private Templates templates;

	public WindowsRedirectorPatchResourceAdapter(Adapter original) throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		this(original, "xml/prefix.xsl");
	}

	public WindowsRedirectorPatchResourceAdapter(Adapter original, String xsltName) throws TransformerConfigurationException, TransformerFactoryConfigurationError {
		super();
		System.out.println("Creating Adapter...");
		this.original = original;
		this.templates = TransformerFactory.newInstance().newTemplates(
				new StreamSource(getClass().getClassLoader()
						.getResourceAsStream(xsltName)));
		System.out.println("...Created Adapter");
	}

	@Override
	public void afterService(Request request, Response response)
			throws Exception {
		System.out.println("after service(..) - called");
		logger.trace("after service(..) - called");
		original.afterService(request, response);
		logger.trace("after service(..) - processed");
		System.out.println("after service(..) - processed");
	}

	// DAV = 1
	// MS-Author-Via = DAV
	// 204 / No Content
	@Override
	public void service(Request request, Response response) throws Exception {
		System.out.println("service");
		logger.trace("service(..) - called");

		final String agent = request.getHeader("user-agent");
		logger.debug("doFilter(..) - user-agent: " + agent);
		
		final boolean isMini = agent != null && agent.contains("MiniRedir");
		if (isMini) {
			final HttpMethod method = HttpMethod.method(request.method()
					.toString());
			logger.debug("doFilter(..) - method: " + method);
			System.out.println("method: "+method);
			
			switch (method) {
			case OPTIONS:
				logger.debug("doFilter(..) - OPTIONS");

				final String uri = request.requestURI().toString();
				final boolean isRoot = uri.equals("/");
				logger
						.debug("doFilter(..) - URI: " + uri + " isRoot? "
								+ isRoot);
				if (isRoot) {
					logger.debug("doFilter(..) - procssing isRoot");
					// if root, options, MiniRedir
					// return noContent, "DAV" = "1", "MS-Author-Via" = "DAV"
					response.setStatus(SC_NO_CONTENT);
					response.setHeader("DAV", "1");
				}
				// if options, MiniRedir
				// return "MS-Author-Via" = "DAV"
				response.setHeader("MS-Author-Via", "DAV");
				break;

			case PROPFIND:
				System.out.println("propfind");
				logger.debug("doFilter(..) - PROPFIND");
				response.addResponseFilter(new ResponseFilter() {
					private StringBuilder builder;
					@Override
					public void filter(ByteChunk bytes) {
						System.out.println("filter");
						StringReader sr = new StringReader(bytes.toString());
						Source xmlSource = new StreamSource(sr);
						System.out.println("original: "+bytes);
						System.out.println("original-length: "+bytes.getLength());
						try {
							final Transformer transformer = templates
									.newTransformer();
							CharArrayWriter caw = new CharArrayWriter();
							StreamResult result = new StreamResult(caw);
							transformer.transform(xmlSource, result);
							bytes.recycle();
							
							byte[] transformedBytes = caw.toString().getBytes();
							System.out.println("transformed: "+caw);
							System.out.println("transformed-length: "+caw.size());
							bytes.setBytes(transformedBytes, 0, transformedBytes.length);
						} catch (Exception ex) {
							logger.error("Error while transforming the XML with XSLT:", ex);
						} 						
					}
				});
				logger.trace("service(..) - delegating service request");
				original.service(request, response);
				logger.trace("service(..) - get response back");

				break;

			default:
				logger.trace("service(..) - delegating service request");
				original.service(request, response);
				logger.trace("service(..) - get response back");
				break;
			}
		}else{
			logger.trace("service(..) - delegating service request");
			original.service(request, response);
			logger.trace("service(..) - get response back");
		}
	}
}
