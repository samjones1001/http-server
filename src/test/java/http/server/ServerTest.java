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
        server.addHandler("HEAD", "/some_path", handler);

        assertEquals(handler, server.getHandlers().get("HEAD").get("/some_path"));
    }

    @Test
    void multiplePathsWithTheSameMethodAreNestedTogether() throws IOException {
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("HEAD", "/some_path", handler);
        server.addHandler("HEAD", "/some_other_path", handler);

        assertEquals(2, server.getHandlers().get("HEAD").size());
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
        server.addHandler("HEAD", "/some_path", handler);

        server.start();
        assertEquals(expectedResponse, out.toString());
    }
}
