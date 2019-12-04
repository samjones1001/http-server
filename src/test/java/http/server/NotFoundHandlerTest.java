package http.server;

import http.server.handlers.NotFoundHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedHeaders = "HTTP/1.1 404 Not Found\r\nConnection: Close\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notFoundHandler = new NotFoundHandler();

        notFoundHandler.handle(response);

        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
