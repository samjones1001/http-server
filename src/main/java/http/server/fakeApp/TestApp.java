package http.server.fakeApp;

import http.server.RequestRouter;
import http.server.Server;
import http.server.handlers.HeadHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class TestApp {
    public static void main(String[] args) {
        String portNumber = args.length > 0 ? args[0] : "5000";
        try {
            RequestRouter router = routerSetup();
            Server server = new Server(new ServerSocket(Integer.parseInt(portNumber)), router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public static RequestRouter routerSetup() {
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
        router.addRoute("/", "GET", ((request, response) -> {}));

        return router;
    }
}
