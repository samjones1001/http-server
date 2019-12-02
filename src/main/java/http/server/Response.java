package http.server;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<>();

    public void setStatusCode(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public String send() {
        StringBuilder response = new StringBuilder("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");

        for (String headerName : headers.keySet()) {
            response.append(headerName).append(": ").append(headers.get(headerName)).append("\r\n");
        }

        return response + "\r\n";
    }
}
