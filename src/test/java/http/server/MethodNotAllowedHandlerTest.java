package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodNotAllowedHandlerTest {
    @Test
    void correctlySetsThePassedResponseAssigningHeadAndOptionsToAllowHeaderByDefault() throws IOException {
        String expectedHeaders = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: HEAD, OPTIONS\r\n\r\n";
        HashMap<String, Handler> methodHandlers = new HashMap<>(){{
            put("HEAD", new HeadHandler());
        }};
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notAllowedHandler = new MethodNotAllowedHandler(methodHandlers);

        notAllowedHandler.handle(response);

        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void correctlySetsAnAlphabetisedAllowHeaderWhereOtherMethodsAllowed() throws IOException {
        String expectedHeaders = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n";
        HashMap<String, Handler> methodHandlers = new HashMap<>() {{
            put("HEAD", new HeadHandler());
            put("GET", new HeadHandler());
        }};
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notAllowedHandler = new MethodNotAllowedHandler(methodHandlers);

        notAllowedHandler.handle(response);

        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
