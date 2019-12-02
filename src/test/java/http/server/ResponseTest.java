package http.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseTest {
    @Test
    void addsAHeaderKeyValuePairToTheHeadersList() {
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.addHeader("Content-Type", "text/html");

        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }

    @Test
    void generatesAndSendsTheHeadersOfAnHTTPResponse() throws IOException {
        String expectedOutput = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.setStatusCode(200, "OK");
        response.addHeader("Content-Type", "text/html");

        response.send();

        assertEquals(expectedOutput, outputStream.toString());
    }
}
