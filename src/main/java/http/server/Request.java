package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Request {
    private BufferedReader in;
    private String method;
    private String path;

    public Request(BufferedReader in) throws IOException {
        this.in = in;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public void parse() throws IOException  {
        String requestLine = in.readLine();
        StringTokenizer tokens = new StringTokenizer(requestLine);
        String[] requestLineComponents = new String[3];

        for (int tokenIndex = 0; tokenIndex < requestLineComponents.length; tokenIndex++) {
            requestLineComponents[tokenIndex] = tokens.nextToken();
        }

        this.method = requestLineComponents[0];
        this.path = requestLineComponents[1];
    }
}
