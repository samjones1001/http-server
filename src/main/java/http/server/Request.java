package http.server;

import java.util.StringTokenizer;

public class Request {
    private String requestText;
    private String method;
    private String path;

    public Request(String requestText) {
        this.requestText = requestText;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public void parse() {
        StringTokenizer tokens = new StringTokenizer(requestText);
        String[] requestLineComponents = new String[3];

        for (int tokenIndex = 0; tokenIndex < requestLineComponents.length; tokenIndex++) {
            requestLineComponents[tokenIndex] = tokens.nextToken();
        }

        this.method = requestLineComponents[0];
        this.path = requestLineComponents[1];
    }
}
