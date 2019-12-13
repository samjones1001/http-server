package http.server.handlers;

import http.server.Handler;

public interface NotFoundHandler {
    static Handler getHandler() {
        return ((request, response) -> {
            response.setStatus(404, "Not Found");
            response.addBody("");
        });
    }
}
