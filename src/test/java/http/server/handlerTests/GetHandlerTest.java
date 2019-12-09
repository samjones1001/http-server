package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.GetHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /some_path HTTP/1.1\r\n\r\n".getBytes()))));
        Request request = rp.parse();
        String expectedHeaders = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Length: 0\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler getHandler = new GetHandler();

        getHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void setsABodyOnTheResponse() throws IOException {
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /some_path HTTP/1.1\r\n\r\n".getBytes()))));
        Request request = rp.parse();

        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler getHandler = new GetHandler();

        getHandler.setResponseValues(request, response);

        assertNotNull(response.getBody());
    }
}
