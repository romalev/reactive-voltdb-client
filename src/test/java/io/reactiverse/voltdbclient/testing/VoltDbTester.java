package io.reactiverse.voltdbclient.testing;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;

public class VoltDbTester {

  private static final Logger log = LoggerFactory.getLogger(VoltDbTester.class);

  @Test
  public void testCreateConnection() {
    Client voltDbClient = null;
    ClientConfig config = null;

    try {
      config = new ClientConfig();
      voltDbClient = ClientFactory.createClient(config);
      voltDbClient.createConnection("http://roman.westeurope.cloudapp.azure.com", 8082);
    } catch (IOException e) {
      log.error("error has occurred", e);
    }
  }
}
