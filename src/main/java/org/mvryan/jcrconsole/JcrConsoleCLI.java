/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mvryan.jcrconsole;

import com.google.inject.Injector;
import com.zoomulus.cli.CLI;
import com.zoomulus.cli.Command;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Session;
import java.io.IOException;
import java.util.List;

public class JcrConsoleCLI extends CLI {
    private static Logger log = LoggerFactory.getLogger(JcrConsoleCLI.class);

    private JcrManager jcrManager;
    private Session session;

    @Inject
    public JcrConsoleCLI(@NotNull final Injector injector,
                         @NotNull final List<Command> commands,
                         @NotNull final JcrManager jcrManager,
                         @NotNull final Session session) {
        super(injector);
        this.jcrManager = jcrManager;
        this.session = session;
        withCommands(commands).withPrompt("jcr > ");
    }

    @Override
    public void shutdown() {
        try {
            jcrManager.close();
        }
        catch (IOException e) {
            log.warn("Exception thrown during shutdown", e);
        }
    }
}
