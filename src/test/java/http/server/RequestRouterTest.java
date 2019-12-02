package http.server;

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
        Map<String, Handler> headHandlers = new HashMap<>();
        Handler headHandler = new HeadHandler();
        Map<String, Handler> errHandlers = new HashMap<>();
        Handler notFoundHandler = new NotFoundHandler();

        headHandlers.put("/some_page", headHandler);
        errHandlers.put("/not_found", notFoundHandler);

        handlers.put("HEAD", headHandlers);
        handlers.put("ERR", errHandlers);

        this.handlers = handlers;
    }

    @Test
    void processesARequestAndSendsAResponseToTheClient() throws IOException {
        String requestText = "HEAD /some_page HTTP/1.1";
        String expectedResponseText = "HTTP/1.1 200 OK\r\nConnection: Close\r\nContent-Type: text/html\r\n\r\n";
        ByteArrayInputStream in = new ByteArrayInputStream(requestText.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MockSocket mockSocket = new MockSocket(in, out);
        RequestRouter router = new RequestRouter(mockSocket, handlers);

        router.run();

        assertEquals(expectedResponseText, out.toString());
    }

    @Test
    void retrievesTheHandlerForThePassedRequest() throws IOException {
        Handler expectedHandler = handlers.get("HEAD").get("/some_page");
        String requestText = "HEAD /some_page HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request(in);
        RequestRouter router = new RequestRouter(new Socket(), handlers);

        request.parse();

        assertEquals(expectedHandler, router.retrieveHandler(request));
    }

    @Test
    void returnsANotFoundHandlerIfNoMatchingHandlerFound() throws IOException {
        Handler expectedHandler = handlers.get("ERR").get("/not_found");
        String requestText = "HEAD /non_existant_page HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(requestText.getBytes(Charset.forName("UTF-8")));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request(in);
        RequestRouter router = new RequestRouter(new Socket(), handlers);

        request.parse();

        assertEquals(expectedHandler, router.retrieveHandler(request));
    }
}
