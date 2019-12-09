package http.server.handlers;

import http.server.Handler;
import http.server.Request;
import http.server.Response;

public class PutHandler implements Handler {
    public void setResponseValues(Request request, Response response) {
        response.setStatusCode(201, "Created");
        response.addHeader("Content-Type", "text/html");
        response.addBody("");
    }
}
