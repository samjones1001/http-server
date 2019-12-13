package http.server.handlers;

import http.server.Handler;

public interface HeadHandler {
    static Handler getHandler() {
        return (((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
        }));
    }
}
