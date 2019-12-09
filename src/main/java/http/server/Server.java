package http.server;

import http.server.handlers.*;

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

    public static void main(String[] args) {
        try {
            RequestRouter router = new RequestRouter();
            router.addRoute("/simple_get", "GET", new GetHandler());
            router.addRoute("/method_options", "GET", new GetHandler());
            router.addRoute("/method_options2", "GET", new GetHandler());
            router.addRoute("/method_options2", "POST", new PostHandler());
            router.addRoute("/method_options2", "PUT", new PutHandler());
            router.addRoute("/get_with_body", "HEAD", new HeadHandler());
            router.addRoute("/echo_body", "POST", new PostHandler());
            router.addRoute("/redirect", "GET", new RedirectHandler());

            Server server = new Server(5000, router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
