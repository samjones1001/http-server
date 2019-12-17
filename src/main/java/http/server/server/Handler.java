package http.server.server;

import http.server.server.request.Request;
import http.server.server.response.Response;

@FunctionalInterface
public interface Handler {
    void setResponseValues(Request request, Response response);
}

