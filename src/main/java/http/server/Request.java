package http.server;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private BufferedReader in;
    private String method;
    private String path;

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

    private void parse() {
        try {
            parseRequestLine();
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
}
