package http.server;

public class HeadHandler implements Handler {
    public void handle(Response response) {
        response.setStatusCode(200, "OK");
        response.addHeader("Content-Type", "text/html");
    }
}
