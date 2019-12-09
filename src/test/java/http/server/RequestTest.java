package http.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    @Test
    void parsesTheInitialLineOfAnHTTPRequest() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new Request(in);

        assertEquals("GET", request.getMethod());
        assertEquals("/some_page.html", request.getPath());
    }

    @Test
    void parsesTheHeadersOfAnHTTPRequest() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\nContent-Type: text/html\r\n\r\n";
        Map<String, String> expectedHeaders = new HashMap<>(){{
            put("Content-Type", "text/html");
        }};
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new Request(in);

        assertEquals(expectedHeaders, request.getHeaders());
    }

    @Test
    void parsesTheBodyOfAnHTTPRequestIfPresent() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1\r\nContent-Type: text/html\r\nContent-Length: 16\r\n\r\nThis is the body";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new Request(in);

        assertEquals("This is the body", request.getBody());
    }
}
