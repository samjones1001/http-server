package http.server.handlers;

import http.server.Handler;

public abstract class HeadHandler {
    public static Handler getHandler() {
        return (((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
        }));
    }
}
