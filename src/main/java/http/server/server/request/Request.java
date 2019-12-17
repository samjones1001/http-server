package http.server.server.request;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams;
    private String body;

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) { this.method = method; }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) { this.path = path; }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() { return this.queryParams; }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void addHeader(String key, String value) { this.headers.put(key, value); }

    public String getBody() {
       return this.body;
    }

    public void setBody(String body) { this.body = body; }
}
