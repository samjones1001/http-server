package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.PostHandler;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostHandlerTest {
    @Test
    void correctlySetsThePassedResponse() throws IOException {
        String expectedResponse = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Length: 16\r\nContent-Type: text/html\r\n\r\nThis is the body";
        String requestString = "POST /some_path HTTP/1.1\r\nContent-Type: text/plain\r\nContent-Length: 16\r\n\r\nThis is the body";
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestString.getBytes()))));

        Request request = rp.parse();
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler postHandler = new PostHandler();

        postHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedResponse, outputStream.toString());
    }
}
