package http.server.handlers;

import http.server.Handler;
import http.server.Response;

public class HeadHandler implements Handler {
    public void setResponseValues(Response response) {
        response.setStatusCode(200, "OK");
        response.addHeader("Content-Type", "text/html");
    }
}
