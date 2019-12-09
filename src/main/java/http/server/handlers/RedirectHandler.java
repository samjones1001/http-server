package http.server.handlers;

import http.server.Handler;
import http.server.Request;
import http.server.Response;

public class RedirectHandler implements Handler {
    public void setResponseValues(Request request, Response response) {
        response.setStatusCode(301, "Moved Permanently");
        response.addHeader("Location", "http://127.0.0.1:5000/simple_get");
        response.addBody("");
    }
}
