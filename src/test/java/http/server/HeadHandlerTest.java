package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadHandlerTest {
    @Test
    void correctlySetsThePassedResponse() {
        String expectedHeaders = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        Response response = new Response();
        Handler headHandler = new HeadHandler();

        headHandler.handle(response);

        assertEquals(expectedHeaders, response.send());
    }
}
