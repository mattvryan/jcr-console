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

import com.google.common.io.Closer;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class JcrManager implements Closeable {
    private final Configuration config;
    private final Closer closer = Closer.create();

    private Repository repository;

    @Inject
    public JcrManager(@NotNull final Configuration config) throws IOException, InvalidFileStoreVersionException {
        this.config = config;
        repository = getOrCreateRepository();
    }

    private Repository getOrCreateRepository() throws IOException, InvalidFileStoreVersionException {
        if (null == repository) {
            FileStore fs = FileStoreBuilder.fileStoreBuilder(new File(config.getProperty(Configuration.REPO_DIR))).build();
            closer.register(asCloseable(fs));

            SegmentNodeStore nodeStore = SegmentNodeStoreBuilders.builder(fs).build();

            Oak oak = new Oak(nodeStore);
            Jcr jcr = new Jcr(oak);
            repository = jcr.createRepository();
        }

        return repository;
    }

    public Session getSession() throws RepositoryException {
        Session session = repository.login(new SimpleCredentials(config.getUsername(), config.getPassword()));
        closer.register(asCloseable(session));
        return session;
    }

    @Override
    public void close() throws IOException {
        closer.close();
    }

    private Closeable asCloseable(@NotNull final FileStore fs) {
        return new Closeable() {
            @Override
            public void close() throws IOException {
                fs.close();
            }
        };
    }

    private Closeable asCloseable(@NotNull final Session session) {
        return new Closeable() {
            @Override
            public void close() throws IOException {
                session.logout();
            }
        };
    }
}
