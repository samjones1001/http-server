package http.server;

import http.server.mocks.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestRouterTest {
    @Test
    void addsANewHandlerToTheListOfHandlers() {
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "HEAD", (request, response) -> {});
        assertEquals(1, router.getRoutes().size());
    }

    @Test
    void multipleMethodsOnTheSameRouteAreNestedTogetherAndDoNotCreateDuplicateEntries() {
        RequestRouter router = new RequestRouter();

        router.addRoute("/some_path", "GET", (request, response) -> {});
        router.addRoute("/some_path", "POST", (request, response) -> {});

        assertEquals(1, router.getRoutes().size());
    }

    @Test
    void processesARequestAndSendsAResponseToTheClient() throws IOException {
        String requestText = "GET /some_path HTTP/1.1\r\n\r\n";
        Handler handler = ((request, response) -> {
           response.setStatus(200, "OK");
           response.addHeader("Content-Type", "text/html");
        });
        String expectedResponseText = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "GET", handler);

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, out.toString());
    }

    @Test
    void returnsANotFoundResponseIfNoMatchingPathFound() throws IOException {
        String requestText = "HEAD /non_existant_page HTTP/1.1\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 404 Not Found\r\nConnection: Close\r\nContent-Length: 0\r\n\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter();

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, out.toString());
    }

    @Test
    void returnsAMethodNotAllowedHandlerIfPathExistsButDoesNotRespondToMethod() throws IOException {
        String requestText = "POST /some_path HTTP/1.1\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\nContent-Type: text/html\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n";

        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "GET", ((request, response) -> {}));


        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, out.toString());
    }
}
