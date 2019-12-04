package http.server.handlers;

import http.server.Handler;
import http.server.Response;

public class GetHandler implements Handler {
    public void handle(Response response) {
        response.setStatusCode(200, "OK");
        response.addHeader("Content-Type", "text/html");
        response.addBody("");
    }
}
