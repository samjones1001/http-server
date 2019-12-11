package http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private RequestRouter router;
    private ServerSocket serverSocket;

    public Server(int port, RequestRouter router) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.router = router;
    }

    public Server(ServerSocket serverSocket, RequestRouter router) {
        this.serverSocket = serverSocket;
        this.router = router;
    }

    public void start() throws IOException {
        Socket client;
        while ((client = serverSocket.accept()) != null) {
            handleConnection(client);
        }
    }

    private void handleConnection(Socket client) {
        router.routeRequest(client);
    }
}
