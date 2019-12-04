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
    private String body;

    public Response(OutputStream out) {
       this.out = out;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() { return this.body; }

    public void setStatusCode(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void addBody(String body) {
        addHeader("Content-Length", Integer.toString(body.length()));
        this.body = body;
    }

    public void send() throws IOException  {
        headers.put("Connection", "Close");
        String response = buildHeaders(buildResponseLine());
        if (body != null) {
            response += body;
        }
        out.write((response).getBytes());
    }

    private String buildResponseLine() {
        return "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n";
    }

    private String buildHeaders(String response) {
        for (String headerName : headers.keySet()) {
            response += buildHeaderLine(headerName);
        }
        return response + "\r\n";
    }

    private String buildHeaderLine(String headerName) {
        return headerName + ": " + headers.get(headerName) + "\r\n";
    }
}
