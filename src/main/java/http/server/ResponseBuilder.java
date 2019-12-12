package http.server;

import java.util.Map;

public class ResponseBuilder {
    public String buildResponse(int statusCode, String statusMessage, Map<String, String> headers, String body) {
        String responseString =  buildHeaders(buildResponseLine(statusCode, statusMessage), headers) ;
        if (body != null) {
            responseString += body;
        }

        return  responseString;
    }

    private String buildResponseLine(int statusCode, String statusMessage) {
        return "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n";
    }

    private String buildHeaders(String response, Map<String, String> headers) {
        for (String headerName : headers.keySet()) {
            response += buildHeaderLine(headerName, headers);
        }
        return response + "\r\n";
    }

    private String buildHeaderLine(String headerName, Map<String, String> headers) {
        return headerName + ": " + headers.get(headerName) + "\r\n";
    }
}
