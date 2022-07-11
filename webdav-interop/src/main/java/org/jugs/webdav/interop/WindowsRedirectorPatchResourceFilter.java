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
package org.jugs.webdav.interop;

import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class WindowsRedirectorPatchResourceFilter implements Filter {
	private static final Logger logger = Logger
			.getLogger(WindowsRedirectorPatchResourceFilter.class.getName());
	private ServletContext context;
	@Override
	public void destroy() {
		this.templates = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		/*
		 * This filter is only able to handle HTTP, so we bypass anything else.
		 */
		if (!(servletRequest instanceof HttpServletRequest)
				|| !(servletResponse instanceof HttpServletResponse)) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		logger.finer("doFilter(..) - called");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		final String agent = request.getHeader("user-agent");
		logger.fine("doFilter(..) - user-agent: " + agent);

		final boolean isMini = agent != null && agent.contains("MiniRedir");
		if (isMini) {
			final HttpMethod method = HttpMethod.method(request.getMethod());
			logger.fine("doFilter(..) - method: " + method + " - original: "+request.getMethod());

			switch (method) {
			case OPTIONS:
				logger.fine("doFilter(..) - OPTIONS");

				final String uri = request.getRequestURI();
				final boolean isRoot = uri.equals("/");
				logger
						.fine("doFilter(..) - URI: " + uri + " isRoot? "
								+ isRoot);
				if (isRoot) {
					logger.fine("doFilter(..) - procssing isRoot");
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
				logger.fine("doFilter(..) - PROPFIND");
				HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
				//if the resource will not be found, there is no xml to process!!
				logger.finest("doFilter(..) - delegating service request");
				chain.doFilter(servletRequest, responseWrapper);
				logger.finest("doFilter(..) - get response back");

				String responseMsg = responseWrapper.toString();
				if(responseMsg.length() > 0){
					// Get response from servlet
					StringReader sr = new StringReader(responseMsg);
					Source xmlSource = new StreamSource(sr);
	
					final PrintWriter out = response.getWriter();
					try {
						final Transformer transformer = this.templates
								.newTransformer();
						ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
						StreamResult result = new StreamResult(baos);
						transformer.transform(xmlSource, result);
						String tranformedResponse = baos.toString();
						response.setContentLength(tranformedResponse.length());
						out.write(tranformedResponse);
					} catch (Exception ex) {
						context.log("Error while transforming the XML with XSLT.", ex);
						out.write(responseMsg);
					} finally {
						out.flush();
						out.close();
					}
				}
				break;

			default:
				logger.finest("doFilter(..) - delegating service request");
				chain.doFilter(servletRequest, servletResponse);
				logger.finest("doFilter(..) - get response back");

				break;
			}
		}else{
			logger.finest("doFilter(..) - delegating service request");
			chain.doFilter(servletRequest, servletResponse);
			logger.finest("doFilter(..) - get response back");
		}
	}

	/**
	 * Precompiled XSLT Style Sheet (for improved performance).
	 */
	private Templates templates;

	@Override
	public void init(FilterConfig config) throws ServletException {
		try {
			context = config.getServletContext();
			String param = config.getInitParameter("interop-xslt");
			if (param == null) {
				param = "xml/prefix.xsl";
			}
			this.templates = TransformerFactory.newInstance().newTemplates(
					new StreamSource(getClass().getClassLoader()
							.getResourceAsStream(param)));
		} catch (final TransformerConfigurationException e) {
			throw new ServletException(e);
		} catch (final TransformerFactoryConfigurationError e) {
			throw new ServletException(e);
		}
	}
}
