package http.server.handlers;

import http.server.Handler;
import http.server.Response;

public class NotFoundHandler implements Handler {
    public void handle(Response response) {
        response.setStatusCode(404, "Not Found");
        response.addBody("");
    }
}
