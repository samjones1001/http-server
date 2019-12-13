package http.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestParser {
    private final BufferedReader in;

    public RequestParser(BufferedReader in) {
        this.in = in;
    }

    public Request parse() {
        try {
            Request request = new Request();
            parseRequestLine(request);
            parseHeaders(request);
            parseBody(request);
            return request;
        } catch (Exception err) {
            throw new ParseException();
        }
    }

    private void parseRequestLine(Request request) throws IOException {
            String requestLine = in.readLine();
            String[] requestLineComponents = requestLine.split(" ", 3);

            request.setMethod(requestLineComponents[0]);
            request.setPath(requestLineComponents[1]);
    }

    private void parseHeaders(Request request) throws IOException {
        String nextHeader;
        while ((nextHeader = in.readLine()).length() > 0) {
            String[] headerComponents = nextHeader.split(": ");
            request.addHeader(headerComponents[0], headerComponents[1]);
        }
    }

    private void parseBody(Request request) throws IOException {
        if (request.getHeaders().containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(request.getHeaders().get("Content-Length"));
            char[] buffer = new char[contentLength];

            in.read(buffer, 0, contentLength);
            String requestBody = new String(buffer, 0, buffer.length);
            request.setBody(requestBody);
        }
    }
}
