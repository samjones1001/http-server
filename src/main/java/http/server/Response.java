package http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private OutputStream out;
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<>();

    public Response(OutputStream out) {
       this.out = out;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setStatusCode(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void send() throws IOException  {
        headers.put("Connection", "Close");
        String response = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n";
        for (String headerName : headers.keySet()) {
            response += (headerName + ": " + headers.get(headerName) + "\r\n");
        }
        out.write((response + "\r\n").getBytes());
    }
}
