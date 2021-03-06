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

package org.mvryan.jcrconsole.commands;

import com.google.common.collect.Lists;
import com.zoomulus.cli.Command;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

public class ExitCommand implements Command {
    @Override
    public @NotNull List<String> getNames() {
        return Lists.newArrayList("exit", "quit", "bye", "x", "q");
    }

    @Override
    public @NotNull String getShortDescription() {
        return "Exits the CLI";
    }

    @Override
    public @NotNull Optional<String> getLongDescription() {
        return Optional.of("Exits the CLI.  Automatically shuts down the repository before exiting.");
    }

    @Override
    public boolean run(@NotNull String commandName, @NotNull List<String> args, @NotNull PrintStream out, @NotNull PrintStream err) {
        return false;
    }
}
