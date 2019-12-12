package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.NotFoundHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String requestString = "POST /some_path HTTP/1.1\r\n\r\n";
        String expectedHeaders = "HTTP/1.1 404 Not Found\r\nConnection: Close\r\nContent-Length: 0\r\n\r\n";
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestString.getBytes()))));
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notFoundHandler = NotFoundHandler.getHandler();

        Request request = rp.parse();
        notFoundHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
