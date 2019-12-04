package http.server.handlerTests;

import http.server.Handler;
import http.server.Response;
import http.server.handlers.GetHandler;
import http.server.handlers.HeadHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Length: 0\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler getHandler = new GetHandler();

        getHandler.handle(response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void setsABodyOnTheResponse() throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler getHandler = new GetHandler();

        getHandler.handle(response);

        assertNotNull(response.getBody());
    }
}
