package http.server;

public class Response {
    private int statusCode;
    private String statusMessage;

    public void setStatusCode(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public String send() {
        return "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n";
    }
}
