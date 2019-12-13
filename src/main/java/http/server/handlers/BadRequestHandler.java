package http.server.handlers;

import http.server.Handler;

public class BadRequestHandler {
    public static Handler getHandler() {
        return (((request, response) -> {
            response.setStatus(400, "Bad Request");
        }));
    }
}
