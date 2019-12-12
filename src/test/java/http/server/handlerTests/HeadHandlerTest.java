package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.HeadHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String requestString = "HEAD /some_path HTTP/1.1\r\n\r\n";
        String expectedHeaders = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestString.getBytes()))));
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler headHandler = HeadHandler.getHandler();

        Request request = rp.parse();
        headHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
