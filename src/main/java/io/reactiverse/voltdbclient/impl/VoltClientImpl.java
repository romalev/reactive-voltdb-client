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
import io.vertx.core.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.voltdb.client.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
public class VoltClientImpl implements VoltClient {

  private final static Logger log = LoggerFactory.getLogger(VoltClientImpl.class);

  private Vertx vertx;
  private Context context;
  private VoltClientOptions voltClientOptions;
  private Client client;

  public VoltClientImpl(Vertx vertx, VoltClientOptions voltClientOptions) {
    Objects.requireNonNull(vertx);
    Objects.requireNonNull(voltClientOptions);
    this.vertx = vertx;
    this.context = vertx.getOrCreateContext();
    this.voltClientOptions = voltClientOptions;
    this.client = ClientFactory.createClient(getClientConfig(voltClientOptions));
  }

  @Override
  public VoltClient createConnection(Handler<AsyncResult<Void>> resultHandler) {
    context.executeBlocking(event -> {
      try {
        log.trace("Trying to connect to: " + voltClientOptions.getHost() + ":" + voltClientOptions.getPort());
        client.createConnection(voltClientOptions.getHost(), 21212);
        log.trace("Connection for: " + voltClientOptions.getHost() + ":" + voltClientOptions.getPort() + " has been created.");
        event.complete();
      } catch (UnknownHostException e) {
        log.error("IP address of a host: " + voltClientOptions.getHost() + " could not be determined.", e);
        event.fail(e);
      } catch (IOException e) {
        log.error("I/O exception of some sort has occurred with connecting to: " + voltClientOptions.getHost() + ":" + voltClientOptions.getPort(), e);
        event.fail(e);
      }
    }, resultHandler);
    return this;
  }

  @Override
  public VoltClient callProcedure(String procName, Object[] parameters, Handler<AsyncResult<ClientResponse>> resultHandler) {
    try {
      boolean queued = client.callProcedure(clientResponse -> {
        resultHandler.handle(Future.succeededFuture(clientResponse));
      }, procName, parameters);

      if (!queued) {
        // to be tested.
      }
    } catch (IOException e) {
      log.error("Error occurred while calling procedure: " + procName, e);
      resultHandler.handle(Future.failedFuture(e));
    }
    return this;
  }

  @Override
  public void close(Handler<AsyncResult<Void>> resultHandler) {
    context.executeBlocking(event -> {
      try {
        client.close();
        log.trace("Client has been closed.");
        event.complete();
      } catch (InterruptedException e) {
        log.error("Failed to close the client.", e);
        event.fail(e);
      }
    }, resultHandler);

  }

  private ClientConfig getClientConfig(VoltClientOptions voltClientOptions) {
    return new ClientConfig(voltClientOptions.getUsername(), voltClientOptions.getPassword(), true, new ClientStatusListenerExt());
  }
}
