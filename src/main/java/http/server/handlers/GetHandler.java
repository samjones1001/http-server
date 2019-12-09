package http.server.handlers;

import http.server.Handler;
import http.server.Request;
import http.server.Response;

public class GetHandler implements Handler {
    public void setResponseValues(Request request, Response response) {
        response.setStatus(200, "OK");
        response.addHeader("Content-Type", "text/html");
        response.addBody("");
    }
}
