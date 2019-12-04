package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.NotFoundHandler;
import http.server.mocks.MockSocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void processesARequestAndSendsAResponseToTheClient() throws IOException {
        String requestText = "HEAD /some_path HTTP/1.1";
        String expectedResponseText = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter(mockSocket, handlers);

        router.routeRequest();

        assertEquals(expectedResponseText, out.toString());
    }

    @Test
    void retrievesTheHandlerForThePassedRequest() throws IOException {
        Handler expectedHandler = handlers.get("/some_path").get("HEAD");
        String requestText = "HEAD /some_path HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request(in);
        RequestRouter router = new RequestRouter(new Socket(), handlers);

        assertEquals(expectedHandler, router.retrieveHandler(request));
    }

    @Test
    void returnsANotFoundHandlerIfNoMatchingHandlerFound() throws IOException {
        Handler expectedHandler = handlers.get("err").get("err");
        String requestText = "HEAD /non_existant_page HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request(in);
        RequestRouter router = new RequestRouter(new Socket(), handlers);

        assertEquals(expectedHandler, router.retrieveHandler(request));
    }
}
