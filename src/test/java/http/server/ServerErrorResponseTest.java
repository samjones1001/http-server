package http.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerErrorResponseTest {
    @Test
    void generatesAnInternalServerErrorResponse() throws IOException {
        String expectedOutput = "HTTP/1.1 500 Internal Server Error\r\nConnection: Close\r\n\r\n";
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new ServerErrorResponse(outputStream);

        response.send();

        assertEquals(expectedOutput, outputStream.toString());
    }
}
