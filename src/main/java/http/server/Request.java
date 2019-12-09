package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private BufferedReader in;
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public Request(BufferedReader in) {
        this.in = in;
        parse();
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getBody() {
       return this.body;
    }

    private void parse() {
        try {
            parseRequestLine();
            parseHeaders();
            parseBody();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    private void parseRequestLine() throws IOException {
        String requestLine = in.readLine();
        String[] requestLineComponents = requestLine.split(" ", 3);
        this.method = requestLineComponents[0];
        this.path = requestLineComponents[1];
    }

    private void parseHeaders() throws IOException {
        String nextHeader;
        while ((nextHeader = in.readLine()).length() > 0) {
            String[] headerComponents = nextHeader.split(": ");
            headers.put(headerComponents[0], headerComponents[1]);
        }
    }

    private void parseBody() throws IOException {
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.valueOf(headers.get("Content-Length"));
            char[] buffer = new char[contentLength];
            String postBody;

            in.read(buffer, 0, contentLength);
            postBody = new String(buffer, 0, buffer.length);
            this.body = postBody;
        }
    }
}
