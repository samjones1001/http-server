package http.server;

import http.server.mocks.MockSocket;
import http.server.server.Handler;
import http.server.server.RequestRouter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestRouterTest {
    private MockSocket setupMockSocket(String requestString) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(requestString.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        return new MockSocket(in, out);
    }

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
        String expectedResponseText = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        MockSocket mockSocket = setupMockSocket(requestText);
        RequestRouter router = new RequestRouter();
        Handler handler = ((request, response) -> {
            response.setStatus(200, "OK");
            response.addHeader("Content-Type", "text/html");
        });

        router.addRoute("/some_path", "GET", handler);
        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, mockSocket.getOutputStream().toString());
    }

    @Test
    void returnsANotFoundResponseIfNoMatchingPathFound() throws IOException {
        String requestText = "HEAD /non_existant_page HTTP/1.1\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 404 Not Found\r\nConnection: Close\r\nContent-Length: 0\r\n\r\n";
        MockSocket mockSocket = setupMockSocket(requestText);
        RequestRouter router = new RequestRouter();

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, mockSocket.getOutputStream().toString());
    }

    @Test
    void returnsAMethodNotAllowedResponseIfPathExistsButDoesNotRespondToMethod() throws IOException {
        String requestText = "POST /some_path HTTP/1.1\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 405 Method Not Allowed\r\nConnection: Close\r\nContent-Length: 0\r\nContent-Type: text/html\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n";
        MockSocket mockSocket = setupMockSocket(requestText);
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "GET", ((request, response) -> {}));

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, mockSocket.getOutputStream().toString());
    }

    @Test
    void returnsABadRequestResponseIfRequestFormatIsInvalid() throws IOException {
        String requestText = "NotAValidRequest\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 400 Bad Request\r\nConnection: Close\r\n\r\n";
        MockSocket mockSocket = setupMockSocket(requestText);
        RequestRouter router = new RequestRouter();

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, mockSocket.getOutputStream().toString());
    }
}
