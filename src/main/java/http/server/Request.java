package http.server;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) { this.method = method; }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) { this.path = path; }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void addHeader(String key, String value) { this.headers.put(key, value); }

    public String getBody() {
       return this.body;
    }

    public void setBody(String body) { this.body = body; }
}
