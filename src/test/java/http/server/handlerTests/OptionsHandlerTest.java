package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.Response;
import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.OptionsHandler;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionsHandlerTest {
    @Test
    void correctlySetsThePassedResponseAssigningHeadAndOptionsToAllowHeaderByDefault() throws IOException {
        Request request = new Request(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("OPTIONS /some_path HTTP/1.1\r\n\r\n".getBytes()))));

        String expectedHeaders = "HTTP/1.1 200 No Content\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: HEAD, OPTIONS\r\n\r\n";
        HashMap<String, Handler> methodHandlers = new HashMap<>(){{
            put("HEAD", new HeadHandler());
        }};
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler optionsHandler = new OptionsHandler(methodHandlers);

        optionsHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void correctlySetsAnAlphabetisedAllowHeaderWhereOtherMethodsAvailable() throws IOException {
        Request request = new Request(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("OPTIONS /some_path HTTP/1.1\r\n\r\n".getBytes()))));

        String expectedHeaders = "HTTP/1.1 200 No Content\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n";
        HashMap<String, Handler> methodHandlers = new HashMap<>() {{
            put("GET", new HeadHandler());
        }};
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler optionsHandler = new OptionsHandler(methodHandlers);

        optionsHandler.setResponseValues(request, response);

        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
