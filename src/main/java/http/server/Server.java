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

    public void addHandler(String path, String method, Handler handler) {
        Map<String, Handler> pathHandlers = handlers.computeIfAbsent(path, key -> new HashMap<>());
        pathHandlers.put(method, handler);
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
            server.addHandler("/simple_get", "GET", new GetHandler());
            server.addHandler("/simple_get", "HEAD", new HeadHandler());
            server.addHandler("/get_with_body", "HEAD", new HeadHandler());
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

    }
}
