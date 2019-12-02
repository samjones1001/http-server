package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    @Test
    void generatesTheInitialLineOfAnHTTPResponse() {
        String expectedOutput = "HTTP/1.1 200 OK\r\n";
        Response response = new Response();

        response.setStatusCode(200, "OK");

        assertEquals(expectedOutput, response.send());
    }
}
