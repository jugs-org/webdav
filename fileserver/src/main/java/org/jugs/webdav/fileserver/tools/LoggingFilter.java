/*
 * Copyright (c) 2023 by Oli B.
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

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse LoggingFilter ...
 *
 * @author oboehm
 * @since 3.0 (16.01.23)
 */
@Provider
@PreMatching
public class LoggingFilter extends BaseFilter implements ContainerRequestFilter, Filter {

    private static final Logger log = LoggerFactory.getLogger(ContainerRequestFilter.class);
    private FilterChainContext.Operation operation;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        log.debug("{} is not filtered", containerRequestContext);
    }

    @Override
    public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
        this.operation = operation;
        return super.createContext(connection, operation);
    }

    @Override
    public NextAction handleRead(FilterChainContext ctx) {
        return logAccess("<- READ", ctx);
    }

    @Override
    public NextAction handleWrite(FilterChainContext ctx) {
        return logAccess("-> WRITE", ctx);
    }

    @Override
    public NextAction handleConnect(FilterChainContext ctx) {
        return logAccess("<- CONNECT", ctx);
    }

    @Override
    public NextAction handleAccept(FilterChainContext ctx) {
        return logAccess("<- ACCEPT", ctx);
    }

    @Override
    public NextAction handleClose(FilterChainContext ctx) {
        return logAccess("-> CLOSE", ctx);
    }

    private static NextAction logAccess(String prefix, FilterChainContext ctx) {
        Connection connection = ctx.getConnection();
        log.info("{} {} {}", connection.getPeerAddress(), prefix, ctx.getMessage());
        return ctx.getInvokeAction();
    }

    @Override
    public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
        Connection connection = ctx.getConnection();
        log.error("{} ** {} {}", connection.getPeerAddress(), operation, ctx.getMessage(), error);
    }

}
