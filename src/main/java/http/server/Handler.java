package http.server;

@FunctionalInterface
public interface Handler {
    void setResponseValues(Request request, Response response);
}

