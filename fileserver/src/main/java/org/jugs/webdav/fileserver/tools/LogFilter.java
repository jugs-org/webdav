/*
 * Copyright (c) 2022 by Oli B.
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
 * (c)reated 31.08.22 by oboehm
 */
package org.jugs.webdav.fileserver.tools;

import com.sun.grizzly.Context;
import com.sun.grizzly.ProtocolFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Die Klasse LogFilter ...
 *
 * @author oboehm
 * @since x.x (31.08.22)
 */
public class LogFilter implements ProtocolFilter {

    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public boolean execute(Context ctx) {
        log.info("execute({}) = true", ctx);
        log.info("{}:{}", ctx.getProtocol(), ctx.getCurrentOpType());
        return true;
    }

    @Override
    public boolean postExecute(Context context) {
        log.info("postExecute({}) = true", context);
        return true;
    }

}
