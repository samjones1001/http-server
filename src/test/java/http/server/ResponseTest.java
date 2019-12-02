package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    @Test
    void generatesTheHeadersOfAnHTTPResponse() {
        String expectedOutput = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        Response response = new Response();

        response.setStatusCode(200, "OK");
        response.addHeader("Content-Type", "text/html");

        assertEquals(expectedOutput, response.send());
    }
}
