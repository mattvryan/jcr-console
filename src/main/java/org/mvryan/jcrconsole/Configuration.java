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

import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.Properties;

public class Configuration extends Properties {
    public static final String APP_DIR = "app.dir";
    public static final String REPO_DIR = "repo.dir";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private Configuration() { super(); }

    public static Configuration from(@NotNull final String args[]) {
        Configuration cfg = new Configuration();

        String appDir = Paths.get(System.getProperty("user.home"), ".jcr-console").toString();

        String repoDir = getRepoDir(appDir);

        cfg.setProperty(APP_DIR, appDir);
        cfg.setProperty(REPO_DIR, repoDir);

        cfg.setProperty(USERNAME, "admin");
        cfg.setProperty(PASSWORD, "admin");

        return cfg;
    }

    private static String getRepoDir(@NotNull final String appDir) {
        return Paths.get(appDir, "repository").toString();
    }

    public String getUsername() {
        return getProperty(USERNAME);
    }

    public char[] getPassword() {
        return getProperty(PASSWORD).toCharArray();
    }
}
