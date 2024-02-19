/*
 * Copyright (c) 2023-2024 by Oli B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 16.01.23 by oboehm
 */
package org.jugs.webdav.fileserver.tools;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.filterchain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * With the LoggingFilter you can record the traffic from and to your
 * WebDAV server. It implements the {@link Filter} interface so you can
 * register it like:
 * <pre>
 *     	HttpServer server = GrizzlyHttpServerFactory.createHttpServer(serverURI, rc);
 *     	NetworkListener listener = server.getListener("grizzly");
 * 		FilterChain filterChain = listener.getFilterChain();
 * 		filterChain.add(new LoggingFilter());
 * </pre>
 *
 * @author oboehm
 * @since 3.0 (16.01.23)
 */
@Provider
@PreMatching
public class LoggingFilter extends BaseFilter implements ContainerRequestFilter, ContainerResponseFilter,
        ClientRequestFilter, Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private FilterChainContext.Operation operation;

    @Override
    public void filter(ContainerRequestContext ctx) {
        log.info("<- {} {}", ctx.getMethod(), ctx);
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        log.info("-> {} {}", requestContext.getMethod(), responseContext);
    }

    @Override
    public void filter(ClientRequestContext requestContext) {
        log.info("<- {}", requestContext.getMethod());
    }

    @Override
    public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
        return logAccess("EVENT " + event, ctx);
    }

    @Override
    public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
        this.operation = operation;
        return super.createContext(connection, operation);
    }

    @Override
    public NextAction handleRead(FilterChainContext ctx) {
        return logAccess("READ", ctx);
    }

    @Override
    public NextAction handleWrite(FilterChainContext ctx) {
        return logAccess("WRITE", ctx);
    }

    @Override
    public NextAction handleConnect(FilterChainContext ctx) {
        return logAccess("CONNECT", ctx);
    }

    @Override
    public NextAction handleAccept(FilterChainContext ctx) {
        return logAccess("ACCEPT", ctx);
    }

    @Override
    public NextAction handleClose(FilterChainContext ctx) {
        return logAccess("CLOSE", ctx);
    }

    private static NextAction logAccess(String op, FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        Context internalCtx = ctx.getInternalContext();
        log.debug("{} {} {}-> {}", connection.getPeerAddress(), op, Objects.toString(ctx.getMessage(), ""), internalCtx.getIoEvent());
        return ctx.getInvokeAction();
    }

    @Override
    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
        Connection connection = ctx.getConnection();
        log.error("{} ** {} {}", connection.getPeerAddress(), operation, Objects.toString(ctx.getMessage(), ""), error);
    }

}
