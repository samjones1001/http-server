package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.Response;
import http.server.handlers.GetHandler;
import http.server.handlers.PutHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        Request request = new Request(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("UTT /some_path HTTP/1.1\r\n\r\nThis is the body".getBytes()))));
        String expectedHeaders = "HTTP/1.1 201 Created\r\nConnection: Close\r\nContent-Length: 0\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler putHandler = new PutHandler();

        putHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
