package no.ntnu.lab;

/**
 * An interface for listeners who want to get notified by the TCP logic whenever an event happened on the
 * server side. Used for dependency reversion - to avoid TcpLogic's dependency on GUI directly.
 */
public interface MessageListener {
    /**
     * Called when an incoming message is received from the server
     * @param message
     */
    void onMessageReceived(String message);

    /**
     * Called when the connection to the server is closed
     */
    void onServerDisconnected();
}
