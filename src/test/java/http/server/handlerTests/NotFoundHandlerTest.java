package http.server.handlerTests;

import http.server.Handler;
import http.server.Response;
import http.server.handlers.NotFoundHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedHeaders = "HTTP/1.1 404 Not Found\r\nConnection: Close\r\nContent-Length: 0\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notFoundHandler = new NotFoundHandler();

        notFoundHandler.setResponseValues(response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
