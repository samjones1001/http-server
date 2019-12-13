package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.BadRequestHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadRequestHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedHeaders = "HTTP/1.1 400 Bad Request\r\nConnection: Close\r\n\r\n";
        Request request = new Request();
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler badRequestHandler = BadRequestHandler.getHandler();

        badRequestHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
