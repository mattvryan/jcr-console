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

import com.google.common.collect.Lists;
import com.google.common.io.Closer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.zoomulus.cli.CLI;
import com.zoomulus.cli.Command;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.jetbrains.annotations.NotNull;
import org.mvryan.jcrconsole.commands.ExitCommand;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.List;

public class JCRConsoleModule extends AbstractModule {
    private Configuration config;
    private JcrManager jcrManager;
    private Session session;

    public JCRConsoleModule(@NotNull final String[] args) throws IOException, InvalidFileStoreVersionException, RepositoryException {
        config = Configuration.from(args);
        jcrManager = new JcrManager(config);
        session = jcrManager.getSession();
    }

    @Override
    public void configure() {
        bind(CLI.class).to(JcrConsoleCLI.class);
        bind(JcrManager.class).toInstance(jcrManager);
        bind(Session.class).toInstance(session);
    }

    @Provides
    public List<Command> getCommandList() {
        return Lists.newArrayList(
                new ExitCommand()
        );
    }
}
