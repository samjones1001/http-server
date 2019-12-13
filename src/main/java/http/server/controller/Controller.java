package http.server.controller;

import http.server.RequestRouter;
import http.server.handlers.HeadHandler;

public class Controller {
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
