package http.server.handlers;

import http.server.Handler;

public class NotFoundHandler {
    public static Handler getHandler() {
        return ((request, response) -> {
            response.setStatus(404, "Not Found");
            response.addBody("");
        });
    }
}
