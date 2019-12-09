package http.server;

import http.server.handlers.GetHandler;
import http.server.handlers.MethodNotAllowedHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTest {
    @Test
    void canAddAMethodToTheListOfMethods() {
        Route route = new Route();
        route.addMethodHandler("GET", new GetHandler());

        assertTrue(route.getMethodHandlers().containsKey("GET"));
    }

    @Test
    void addsDefaultMethodsIfNotAlreadyPresent() {
        Route route = new Route();
        route.addMethodHandler("GET", new GetHandler());

        assertTrue(route.getMethodHandlers().containsKey("HEAD"));
        assertTrue(route.getMethodHandlers().containsKey("OPTIONS"));
    }

    @Test
    void canRetrieveTheHandlerForAGivenMethod() {
        Route route = new Route();
        Handler expectedHandler = new GetHandler();
        route.addMethodHandler("GET", expectedHandler);

        assertEquals(expectedHandler, route.getMethodHandler("GET"));
    }

    @Test
    void returnsAMethodNotAllowedHandlerIfPassedUnavailableMethod() {
        Route route = new Route();
        Handler notFoundHandler = route.getMethodHandler("POST");

        assertTrue(notFoundHandler instanceof MethodNotAllowedHandler);
    }
}
