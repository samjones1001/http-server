package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.Response;
import http.server.handlers.GetHandler;
import http.server.handlers.RedirectHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedirectHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        Request request = new Request(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("GET /some_path HTTP/1.1\r\n\r\n".getBytes()))));
        String expectedHeaders = "HTTP/1.1 301 Moved Permanently\r\nConnection: Close\r\nLocation: http://0.0.0.0:5000/simple_get\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler redirectHandler = new RedirectHandler();

        redirectHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
