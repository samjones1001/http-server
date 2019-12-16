package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            setPathAndQueryParams(request, requestLineComponents[1]);
    }

    private void setPathAndQueryParams(Request request, String url) {
        String[] urlElements = url.split("\\?");
        request.setPath(urlElements[0]);

        if (urlElements.length > 1) {
            request.setQueryParams(splitQueryParams(urlElements[1]));
        }
    }

    private Map<String, String> splitQueryParams(String queryParams) {
        String[] paramsList = queryParams.split("&");
        Map<String, String> params = new HashMap<>();

        for (String paramPair: paramsList) {
            String[] values = paramPair.split("=");
            params.put(values[0], values[1]);
        }

        return params;
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
