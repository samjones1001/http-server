package http.server.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private OutputStream out;
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public Response(OutputStream out) {
       this.out = out;
    }

    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public byte[] getBody() { return this.body; }

    public void addBody(byte[] body) {
        addHeader("Content-Length", Integer.toString(body.length));
        this.body = body;
    }

    public void send() throws IOException  {
        headers.put("Connection", "Close");
        ResponseBuilder builder = new ResponseBuilder();
        byte[] response = builder.buildResponse(statusCode, statusMessage, headers, body);
        out.write((response));
    }
}