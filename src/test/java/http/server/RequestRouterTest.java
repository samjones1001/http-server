package http.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        headHandlers.put("/some_page", headHandler);

        handlers.put("HEAD", headHandlers);

        this.handlers = handlers;
    }

    @Test
    void retrievesTheHandlerForThePassedRequest() {
        Handler expectedHandler = handlers.get("HEAD").get("/some_page");
        Request request = new Request("HEAD /some_page HTTP/1.1");

        request.parse();
        RequestRouter router = new RequestRouter(handlers);

        assertEquals(expectedHandler, router.retrieveHandler(request));
    }
}
