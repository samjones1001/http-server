package http.server.server.handlers;

import http.server.server.Handler;

public interface NotFoundHandler {
    static Handler getHandler() {
        return ((request, response) -> {
            response.setStatus(404, "Not Found");
            response.addBody("".getBytes());
        });
    }
}
