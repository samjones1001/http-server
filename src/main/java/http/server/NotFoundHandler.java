package http.server;

public class NotFoundHandler implements Handler {
    public void handle(Response response) {
        response.setStatusCode(404, "Not Found");
    }
}
