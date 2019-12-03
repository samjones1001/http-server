package http.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    @Test
    void parsesTheInitialLineOfAnHTTPRequest() throws IOException {
        String requestText = "GET /some_page.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new Request(in);

        assertEquals("GET", request.getMethod());
        assertEquals("/some_page.html", request.getPath());
    }
}
