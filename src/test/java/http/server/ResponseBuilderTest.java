package http.server;

import http.server.server.response.ResponseBuilder;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseBuilderTest {
    @Test
    void correctlyBuildsAResponseStringWhenNoBodyIsRequired() {
        String expectedOutput = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        Map<String, String> headers = new HashMap<>() {{
            put("Connection", "Close");
            put("Content-Type", "text/html");
        }};
        ResponseBuilder responseBuilder = new ResponseBuilder();
        assertEquals(expectedOutput, responseBuilder.buildResponse(200, "OK", headers, null));
    }

    @Test
    void correctlyBuildsAResponseStringWhenABodyIsRequired() {
        String expectedOutput = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\nThis is the body";
        Map<String, String> headers = new HashMap<>() {{
            put("Connection", "Close");
            put("Content-Type", "text/html");
        }};
        ResponseBuilder responseBuilder = new ResponseBuilder();

        assertEquals(expectedOutput, responseBuilder.buildResponse(200, "OK", headers, "This is the body"));
    }
}
