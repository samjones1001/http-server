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

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
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
            router.addHandler("/simple_get", "GET", new GetHandler());
            router.addHandler("/method_options", "GET", new GetHandler());
            router.addHandler("/method_options2", "GET", new GetHandler());
            router.addHandler("/method_options2", "POST", new PostHandler());
            router.addHandler("/method_options2", "PUT", new PutHandler());
            router.addHandler("/get_with_body", "HEAD", new HeadHandler());
            router.addHandler("/echo_body", "POST", new PostHandler());
            router.addHandler("/redirect", "GET", new RedirectHandler());

            Server server = new Server(5000, router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
