package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public class HTTPServer {
    private Map<String, Map<String, Handler>> handlers = new HashMap<>();
    private Server server;
    private int port;

    public HTTPServer(int port) {
        this.port = port;
        this.server = new ServerSocketWrapper();
    }

    public HTTPServer(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    public Map<String, Map<String, Handler>> getHandlers() {
        return handlers;
    }

    public void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> methodHandlers = handlers.get(method);
        if (methodHandlers == null)  {
            methodHandlers = new HashMap<String, Handler>();
            handlers.put(method, methodHandlers);
        }
        methodHandlers.put(path, handler);
    }

    public void start() throws IOException {
        server.start(port);
        Socket client;

        while ((client = server.accept()) != null) {
            RequestRouter router = new RequestRouter(client, handlers);
//            Thread thread = new Thread(router);
//            thread.start();
            router.run();
        }
    }

    public static void main(String[] args) throws IOException {
        HTTPServer server = new HTTPServer(5000);
        server.addHandler("HEAD", "/simple_get", new HeadHandler());
        server.addHandler("ERR", "/not_found", new NotFoundHandler());
        server.start();
    }
}
