package http.server;

import http.server.handlers.HeadHandler;
import http.server.mocks.MockServerSocket;
import http.server.mocks.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    @Test
    void addsANewHandlerToTheListOfHandlers() throws IOException {
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("/some_path", "HEAD", handler);
        assertEquals(handler, server.getHandlers().get("/some_path").get("HEAD"));
    }

    @Test
    void multiplePathsWithTheSameMethodAreNestedTogether() throws IOException {
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("/some_path", "/GET", handler);
        server.addHandler("/some_path", "/POST", handler);

        assertEquals(2, server.getHandlers().get("/some_path").size());
    }

    @Test
    void acceptsAndProcessesAConnection() throws IOException {
        String expectedResponse = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        String requestText = "HEAD /some_path HTTP/1.1";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in ,out);
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket(mockSocket));
        server.addHandler("/some_path", "HEAD", handler);

        server.start();
        assertEquals(expectedResponse, out.toString());
    }
}
