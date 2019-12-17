package http.server.handlerTests;

import http.server.server.Handler;
import http.server.server.request.Request;
import http.server.server.request.RequestParser;
import http.server.server.response.Response;
import http.server.server.handlers.OptionsHandler;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionsHandlerTest {
    private OutputStream handleRequestAndReturnOutputStream(String requestString, Set<String> allowedMethods) throws IOException {
        RequestParser rp = new RequestParser(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestString.getBytes()))));
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        Handler optionsHandler = OptionsHandler.getHandler(allowedMethods);

        Request request = rp.parse();
        optionsHandler.setResponseValues(request, response);
        response.send();

        return outputStream;
    }

    @Test
    void correctlySetsThePassedResponseAssigningHeadAndOptionsToAllowHeaderByDefault() throws IOException {
        String requestString = "OPTIONS /some_path HTTP/1.1\r\n\r\n";
        String expectedHeaders = "HTTP/1.1 200 No Content\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: HEAD, OPTIONS\r\n\r\n";
        Set<String> allowedMethods = new HashSet<>(Arrays.asList("HEAD", "OPTIONS"));
        OutputStream outputStream = handleRequestAndReturnOutputStream(requestString, allowedMethods);

        assertEquals(expectedHeaders, outputStream.toString());
    }

    @Test
    void correctlySetsAnAlphabetisedAllowHeaderWhereOtherMethodsAvailable() throws IOException {
        String requestString = "OPTIONS /some_path HTTP/1.1\r\n\r\n";
        String expectedHeaders = "HTTP/1.1 200 No Content\r\nConnection: Close\r\nContent-Length: 0\r\n" +
                "Content-Type: text/html\r\nAllow: GET, HEAD, OPTIONS, POST\r\n\r\n";
        Set<String> allowedMethods = new HashSet<>(Arrays.asList("HEAD", "OPTIONS", "GET", "POST"));
        OutputStream outputStream = handleRequestAndReturnOutputStream(requestString, allowedMethods);

        assertEquals(expectedHeaders, outputStream.toString());
    }
}
