package com.hubspot.imap;

import com.hubspot.imap.ImapConfiguration.AuthType;
import com.hubspot.imap.client.ImapClient;
import com.hubspot.imap.protocol.exceptions.ConnectionClosedException;
import com.hubspot.imap.utils.GmailUtils;

import java.util.concurrent.ExecutionException;

public class TestUtils {

  public static String USER_NAME = "hsimaptest1@gmail.com";
  public static String PASSWORD = "***REMOVED***";

  public static String ALL_MAIL = "[Gmail]/All Mail";

  public static final ImapClientFactory CLIENT_FACTORY = new ImapClientFactory(
      new ImapConfiguration.Builder()
          .setAuthType(AuthType.PASSWORD)
          .setHostAndPort(GmailUtils.GMAIL_HOST_PORT)
          .setNoopKeepAliveIntervalSec(10)
          .setUseEpoll(true)
          .build()
  );

  public static ImapClient getClient() throws InterruptedException {
    return CLIENT_FACTORY.connect(USER_NAME, PASSWORD);
  }

  public static ImapClient getLoggedInClient() throws ExecutionException, InterruptedException, ConnectionClosedException {
    ImapClient client = getClient();
    client.login();
    client.awaitLogin();

    return client;
  }
}
