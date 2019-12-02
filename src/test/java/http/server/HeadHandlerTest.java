package http.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler headHandler = new HeadHandler();

        headHandler.handle(response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
