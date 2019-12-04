package http.server;

import http.server.handlers.GetHandler;
import http.server.handlers.HeadHandler;
import http.server.handlers.NotFoundHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Map<String, Map<String, Handler>> handlers = new HashMap<>();
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Map<String, Map<String, Handler>> getHandlers() {
        return handlers;
    }

    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> methodHandlers = handlers.computeIfAbsent(method, key -> new HashMap<>());
        methodHandlers.put(path, handler);
    }

    public void start() throws IOException {
        Socket client;
        while ((client = serverSocket.accept()) != null) {
            handleConnection(client);
        }
    }

    private void handleConnection(Socket client) {
        RequestRouter router = new RequestRouter(client, handlers);
        router.routeRequest();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(5000);
            server.addHandler("HEAD", "/simple_get", new HeadHandler());
            server.addHandler("ERR", "/not_found", new NotFoundHandler());
            server.addHandler("HEAD", "/get_with_body", new HeadHandler());
            server.addHandler("GET", "/simple_get", new GetHandler());
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

    }
}
