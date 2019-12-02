package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    @Test
    void parsesTheInitialLineOfAnHTTPRequest() {
        String requestText = "GET /some_page.html HTTP/1.1";
        Request request = new Request(requestText);
        request.parse();

        assertEquals("GET", request.getMethod());
        assertEquals("/some_page.html", request.getPath());
    }
}
