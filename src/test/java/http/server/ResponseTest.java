package http.server;

import http.server.server.response.Response;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

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
        byte[] body = "This is the body".getBytes();
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.addBody(body);

        assertEquals(body, response.getBody());
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
        response.addBody("This is a body".getBytes());

        response.send();

        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void sendsImageDataAsBytesIfPresent() throws IOException {
        File image = new File("./assets/img/example.jpg");
        byte[] imageData = (Files.readAllBytes(image.toPath()));
        OutputStream fileOutputStream = new ByteArrayOutputStream();
        Files.copy(image.toPath(), fileOutputStream);

        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);

        response.setStatus(200, "OK");
        response.addHeader("Content-Type", "image/jpeg");
        response.addBody(imageData);
        response.send();

        assertTrue(outputStream.toString().contains(fileOutputStream.toString()));
    }
}
