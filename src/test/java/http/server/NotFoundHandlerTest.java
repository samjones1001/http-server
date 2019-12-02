package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundHandlerTest {
    @Test
    void correctlySetsThePassedResponse() {
        String expectedHeaders = "HTTP/1.1 404 Not Found\r\n\r\n";
        Response response = new Response();
        Handler notFoundHandler = new NotFoundHandler();

        notFoundHandler.handle(response);

        assertEquals(expectedHeaders, response.send());
    }
}
