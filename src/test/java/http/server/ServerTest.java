package http.server;

import http.server.handlers.HeadHandler;
import http.server.mocks.MockServerSocket;
import http.server.mocks.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {


    @Test
    void acceptsAndProcessesAConnection() throws IOException {
        String requestText = "HEAD /some_path HTTP/1.1\r\n\r\n";
        String expectedResponse = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in ,out);
        Handler handler = HeadHandler.getHandler();
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "HEAD", handler);

        Server server = new Server(new MockServerSocket(mockSocket), router);

        server.start();
        assertEquals(expectedResponse, out.toString());
    }
}
