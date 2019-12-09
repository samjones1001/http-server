package http.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseTest {
    @Test
    void addsAHeaderKeyValuePairToTheHeadersList() {
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.addHeader("Content-Type", "text/html");

        assertEquals("text/html", response.getHeaders().get("Content-Type"));
    }

    @Test
    void addsABodyAndAContentLengthHeader() {
        String bodyText = "This is the body";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.addBody(bodyText);

        assertEquals(bodyText, response.getBody());
        assertTrue(response.getHeaders().containsKey("Content-Length") && response.getHeaders().get("Content-Length") != null);
    }

    @Test
    void generatesAndSendsTheHeadersOfAnHTTPResponse() throws IOException {
        String expectedOutput = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.setStatus(200, "OK");
        response.addHeader("Content-Type", "text/html");

        response.send();

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void generatesAndSendsHeadersAndBodyIfBodyIsPresent() throws IOException {
        String expectedOutput = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Length: 14\r\nContent-Type: text/html\r\n\r\nThis is a body";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.setStatus(200, "OK");
        response.addHeader("Content-Type", "text/html");
        response.addBody("This is a body");

        response.send();

        assertEquals(expectedOutput, outputStream.toString());
    }
}
