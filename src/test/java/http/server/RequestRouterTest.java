package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.NotFoundHandler;
import http.server.mocks.MockSocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestRouterTest {
    private Map<String, Map<String, Handler>> handlers;

    @BeforeEach
    void setupHandlers() {
        Map<String, Map<String, Handler>> handlers = new HashMap<>();
        Map<String, Handler> pathHandlers = new HashMap<>();
        Handler headHandler = new HeadHandler();
        Map<String, Handler> errHandlers = new HashMap<>();
        Handler notFoundHandler = new NotFoundHandler();

        pathHandlers.put("HEAD", headHandler);
        errHandlers.put("err", notFoundHandler);

        handlers.put("/some_path", pathHandlers);
        handlers.put("err", errHandlers);
        this.handlers = handlers;
    }

    @Test
    void addsANewHandlerToTheListOfHandlers() throws IOException {
        Handler handler = new HeadHandler();
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "HEAD", handler);
        assertEquals(1, router.getRoutes().size());
    }

    @Test
    void multipleMethodsOnTheSameRouteAreNestedTogetherAndDoNotCreateDuplicateEntries() throws IOException {
        Handler handler = new HeadHandler();
        RequestRouter router = new RequestRouter();

        router.addRoute("/some_path", "GET", handler);
        router.addRoute("/some_path", "POST", handler);

        assertEquals(1, router.getRoutes().size());
    }

    @Test
    void processesARequestAndSendsAResponseToTheClient() throws IOException {
        String requestText = "HEAD /some_path HTTP/1.1\r\n\r\n";
        String expectedResponseText = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "HEAD", new HeadHandler());

        router.routeRequest(mockSocket);

        assertEquals(expectedResponseText, out.toString());
    }

    @Test
    void retrievesTheHandlerForThePassedRequest() throws IOException {
        Handler expectedHandler = handlers.get("/some_path").get("HEAD");
        String requestText = "HEAD /some_path HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "HEAD", new HeadHandler());

        assertTrue(router.retrieveHandler(request) instanceof HeadHandler);
    }

    @Test
    void returnsANotFoundHandlerIfNoMatchingHandlerFound() throws IOException {
        String requestText = "HEAD /non_existant_page HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();
        RequestRouter router = new RequestRouter();

        assertTrue(router.retrieveHandler(request) instanceof NotFoundHandler);
    }

    @Test
    void returnsAMethodNotAllowedHandlerIfPathExistsButDoesNotRespondToMethod() {
        String requestText = "POST /some_path HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();
        RequestRouter router = new RequestRouter();
        router.addRoute("/some_path", "GET", new HeadHandler());


        assertTrue(router.retrieveHandler(request) instanceof MethodNotAllowedHandler);
    }
}
