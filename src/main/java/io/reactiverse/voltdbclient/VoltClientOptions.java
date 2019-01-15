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

import io.vertx.codegen.annotations.DataObject;

/**
 * Options used to configure and create instance of {@link VoltClient}.
 *
 * @author <a href="mailto:roman.levytskyi.oss@gmail.com">Roman Levytskyi</a>
 */
@DataObject(generateConverter = true)
public class VoltClientOptions {

  /**
   * Default non-admin port number for volt cluster instances.
   */
  public static final int VOLTDB_SERVER_PORT = 21212;

  private String host;
  private int port;
  private String username;
  private String password;
  // TODO: m_listener = null;
  // TODO: m_cleartext = true;


  public String getHost() {
    return host;
  }

  public VoltClientOptions setHost(String host) {
    this.host = host;
    return this;
  }

  public int getPort() {
    return port;
  }

  public VoltClientOptions setPort(int port) {
    this.port = port;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public VoltClientOptions setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public VoltClientOptions setPassword(String password) {
    this.password = password;
    return this;
  }
}
