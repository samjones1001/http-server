package http.server.routing.setup;

import http.server.RequestRouter;
import http.server.handlers.HeadHandler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RouteSetup {
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
        router.addRoute("/poem", "GET", ((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
            String body = readFile("./resources/html/poem.html");
//            System.out.println(body);
            response.addBody(body);
        }));
        router.addRoute("/", "GET", ((request, response) -> {}));


        return router;
    }

    private static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException err) {
            System.out.println(err.getMessage());
            return null;
        }
    }
}
