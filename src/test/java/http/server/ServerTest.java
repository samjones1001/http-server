package http.server;

import http.server.handlers.GetHandler;
import http.server.handlers.HeadHandler;
import http.server.mocks.MockServerSocket;
import http.server.mocks.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {
    @Test
    void addsANewHandlerToTheListOfHandlers() throws IOException {
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("/some_path", "HEAD", handler);
        assertEquals(handler, server.getHandlers().get("/some_path").get("HEAD"));
    }

    @Test
    void addingANewPathToTheListOfHandlersAlsoAddsDefaultMethods() throws IOException {
        Handler handler = new GetHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("/some_path", "GET", handler);

        assertTrue(server.getHandlers().get("/some_path").containsKey("HEAD"));
        assertTrue(server.getHandlers().get("/some_path").containsKey("OPTIONS"));
    }

    @Test
    void multipleMethodsOnTheSamePathAreNestedTogether() throws IOException {
        Handler handler = new HeadHandler();
        Server server = new Server(new MockServerSocket());
        server.addHandler("/some_path", "/GET", handler);
        server.addHandler("/some_path", "/POST", handler);
        int numberOfMethodsAddedPlusDefaultMethods = 3;
        assertEquals(numberOfMethodsAddedPlusDefaultMethods, server.getHandlers().get("/some_path").size());
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
