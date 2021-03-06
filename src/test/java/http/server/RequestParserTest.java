package http.server;

import http.server.server.request.Request;
import http.server.server.request.RequestParser;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParserTest {
    @Test
    void parsesTheInitialLineOfAnHTTPRequest() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();

        assertEquals("GET", request.getMethod());
        assertEquals("/some_page.html", request.getPath());
    }

    @Test
    void parsesQueryParamatersWherePresent() throws IOException {
        String requestText = "GET /some_page.html?param1=hello&param2=goodbye HTTP/1.1\r\n\r\n";
        Map<String, String> expectedParams = new HashMap<>() {{
            put("param1", "hello");
            put("param2", "goodbye");
        }};
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();

        assertEquals(expectedParams, request.getQueryParams());
    }

    @Test
    void parsesTheHeadersOfAnHTTPRequest() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\nContent-Type: text/html\r\n\r\n";
        Map<String, String> expectedHeaders = new HashMap<>(){{
            put("Content-Type", "text/html");
        }};
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();

        assertEquals(expectedHeaders, request.getHeaders());
    }

    @Test
    void parsesTheBodyOfAnHTTPRequestIfPresent() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\nContent-Type: text/html\r\nContent-Length: 16\r\n\r\nThis is the body";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();

        assertEquals("This is the body", request.getBody());
    }
}
