package http.server.server.response;

import http.server.server.exceptions.BuildResponseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseBuilder {
    public byte[] buildResponse(int statusCode, String statusMessage, Map<String, String> headers, byte[] body) {
        ByteArrayOutputStream responseBuilder = new ByteArrayOutputStream();
        try {
            responseBuilder.write(buildHeaders(buildResponseLine(statusCode, statusMessage), headers));
            if (body != null) {
                responseBuilder.write(body);
            }
        } catch (IOException err){
            throw new BuildResponseException();
        }
        return responseBuilder.toByteArray();
    }

    private byte[] buildResponseLine(int statusCode, String statusMessage) {
        return ("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n").getBytes();
    }

    private byte[] buildHeaders(byte[] response, Map<String, String> headers) throws IOException {
        ByteArrayOutputStream responseBuilder = new ByteArrayOutputStream();
        responseBuilder.write(response);

        for (String headerName : headers.keySet()) {
            responseBuilder.write(buildHeaderLine(headerName, headers));
        }

        responseBuilder.write("\r\n".getBytes());
        return responseBuilder.toByteArray();
    }

    private byte[] buildHeaderLine(String headerName, Map<String, String> headers) {
        return (headerName + ": " + headers.get(headerName) + "\r\n").getBytes();
    }
}
