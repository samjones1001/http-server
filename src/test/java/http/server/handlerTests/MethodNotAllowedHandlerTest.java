package http.server.handlerTests;

import http.server.Handler;
import http.server.Request;
import http.server.RequestParser;
import http.server.Response;
import http.server.handlers.MethodNotAllowedHandler;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodNotAllowedHandlerTest {
    @Test
    void correctlySetsThePassedResponseAssigningHeadAndOptionsToAllowHeaderByDefault() throws IOException {
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("POST /some_path HTTP/1.1\r\n\r\n".getBytes()))));
        Request request = rp.parse();

        String expectedHeaders = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: HEAD, OPTIONS\r\n\r\n";
        Set<String> allowedMethods = new HashSet<>(Arrays.asList("HEAD", "OPTIONS"));
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notAllowedHandler = MethodNotAllowedHandler.getHandler(allowedMethods);

        notAllowedHandler.setResponseValues(request, response);
        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void correctlySetsAnAlphabetisedAllowHeaderWhereOtherMethodsAvailable() throws IOException {
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream("POST /some_path HTTP/1.1\r\n\r\n".getBytes()))));
        Request request = rp.parse();

        String expectedHeaders = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: GET, HEAD, OPTIONS, POST\r\n\r\n";
        Set<String> allowedMethods = new HashSet<>(Arrays.asList("HEAD", "OPTIONS", "GET", "POST"));

        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler notAllowedHandler = MethodNotAllowedHandler.getHandler(allowedMethods);


        notAllowedHandler.setResponseValues(request, response);

        response.send();

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
