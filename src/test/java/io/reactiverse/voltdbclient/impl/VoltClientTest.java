/*
 * Copyright (C) 2019 Roman Levytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactiverse.voltdbclient.impl;

import io.reactiverse.voltdbclient.VoltClient;
import io.reactiverse.voltdbclient.VoltClientOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link io.reactiverse.voltdbclient.VoltClient}
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 * <p>
 * Notes:
 * - running voltdb ./voltdb start --dir=/home/roman/development/software/voltdb/voltdb-working-directoty/voltdbroot --http=8082
 * - enabling slf4 logging: -ea -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory
 */
@RunWith(VertxUnitRunner.class)
public class VoltClientTest {

  private static final String HOST = "roman.westeurope.cloudapp.azure.com";
  private static final int PORT = 8082;
  private static final String USERNAME = "test";
  private static final String PASSWORD = "test";

  @Rule
  public RunTestOnContext rule = new RunTestOnContext();
  private Vertx vertx;
  private VoltClient voltClient;

  @Before
  public void setUp(TestContext context) {
    Async async = context.async();
    vertx = rule.vertx();

    voltClient = VoltClient.create(vertx, new VoltClientOptions()
      .setHost(HOST)
      .setPort(PORT)
      .setUsername(USERNAME)
      .setPassword(PASSWORD));

    async.complete();
  }

  @Test
  public void testCreateConnection(TestContext context) {
    Async async = context.async();
    voltClient.createConnection(event -> {
      assertTrue(event.succeeded());
      async.complete();
    });
  }


  @After
  public void tearDown(TestContext context) {
    voltClient.close(event -> {
      vertx.close(context.asyncAssertSuccess());
    });
  }
}
