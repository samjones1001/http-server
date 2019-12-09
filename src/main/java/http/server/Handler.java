package http.server;

public interface Handler {
    void setResponseValues(Request request, Response response);
}

