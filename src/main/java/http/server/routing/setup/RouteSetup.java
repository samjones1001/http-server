package http.server.routing.setup;

import http.server.server.RequestRouter;
import http.server.server.handlers.HeadHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RouteSetup {
    public static RequestRouter routerSetup() {
        RequestRouter router = new RequestRouter();

        router.addRoute("/", "GET", ((request, response) -> {}));

        router.addRoute("/simple_get", "GET", ((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addBody("".getBytes());
        }));

        router.addRoute("/method_options", "GET", ((request, response) -> {}));

        router.addRoute("/method_options2", "GET", ((request, response) -> {}))
                .addMethodHandler("POST", ((request, response) -> {}))
                .addMethodHandler("PUT", ((request, response) -> {}));

        router.addRoute("/get_with_body", "HEAD", HeadHandler.getHandler());

        router.addRoute("/echo_body", "POST", ((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
            response.addBody(request.getBody().getBytes());
        }));

        router.addRoute("/redirect", "GET", ((request, response) -> {
            response.setStatus(301, "Moved Permanently");
            response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
            response.addBody("".getBytes());
        }));

        router.addRoute("/poem", "GET", ((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
            String body = readFile("./assets/html/poem.html");
            response.addBody(body.getBytes());
        }));

        router.addRoute("/letter", "POST", (((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
            String body = readFile("./assets/html/letter.html");

            for (String paramKey: request.getQueryParams().keySet()) {
                String key = request.getQueryParams().get(paramKey).replace('%', ' ');
                body = body.replaceAll("\\{" + paramKey + "}", key);
            }
            response.addBody(body.getBytes());
        })));

        router.addRoute("/image", "GET", (((request, response) -> {
            File image = new File("./assets/img/example.jpg");
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "image/jpg");
            try {
                response.addBody(Files.readAllBytes(image.toPath()));
            } catch (IOException err) {}
        })));

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
