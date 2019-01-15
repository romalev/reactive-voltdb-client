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
package io.reactiverse.voltdbclient;

import io.reactiverse.voltdbclient.impl.VoltClientImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * VoltDb client that can connect to one or more nodes in a volt cluster.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 * @See {@link org.voltdb.client.Client}
 */
@VertxGen
public interface VoltClient {

  /**
   * Default non-admin port number for volt cluster instances.
   */
  public static final int VOLTDB_SERVER_PORT = 21212;

  static VoltClient create(Vertx vertx) {
    return new VoltClientImpl(vertx, new VoltClientOptions());
  }

  static VoltClient create(Vertx vertx, VoltClientOptions options) {
    return new VoltClientImpl(vertx, options);
  }


  /**
   * Create a connection to a VoltDB node and add it to the set of connections.
   *
   * @param resultHandler holds the result of the created connection.
   */
  @Fluent
  VoltClient createConnection(Handler<AsyncResult<Void>> resultHandler);

  /**
   * Close the client and release its resources.
   *
   * @param resultHandler can be used to indicate whether client was closed properly.
   */
  void close(Handler<AsyncResult<Void>> resultHandler);
}
