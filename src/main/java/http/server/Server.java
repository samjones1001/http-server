package http.server;

import http.server.handlers.HeadHandler;

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
            router.addRoute("/simple_get", "GET", ((request, response) -> {
                response.setStatus(200, "OK");
                response.addHeader("Content-Type", "text/html");
                response.addBody("");
            }));
            router.addRoute("/method_options", "GET", ((request, response) -> {}));
            router.addRoute("/method_options2", "GET", ((request, response) -> {}));
            router.addRoute("/method_options2", "POST", ((request, response) -> {}));
            router.addRoute("/method_options2", "PUT", ((request, response) -> {}));
            router.addRoute("/get_with_body", "HEAD", HeadHandler.getHandler());
            router.addRoute("/echo_body", "POST", ((request, response) -> {
                response.setStatus(200, "OK");
                response.addHeader("Content-Type", "text/html");
                response.addBody(request.getBody());
            }));
            router.addRoute("/redirect", "GET", ((request, response) -> {
                response.setStatus(301, "Moved Permanently");
                response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
                response.addBody("");
            }));

            Server server = new Server(5000, router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
