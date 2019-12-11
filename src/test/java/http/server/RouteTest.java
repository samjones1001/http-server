package http.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTest {
    @Test
    void canAddAMethodToTheListOfMethods() {
        Route route = new Route();
        route.addMethodHandler("GET", ((request, response) -> {}));

        assertTrue(route.getMethodHandlers().containsKey("GET"));
    }

    @Test
    void addsDefaultMethodsIfNotAlreadyPresent() {
        Route route = new Route();
        route.addMethodHandler("GET", ((request, response) -> {}));

        assertTrue(route.getMethodHandlers().containsKey("HEAD"));
        assertTrue(route.getMethodHandlers().containsKey("OPTIONS"));
    }

    @Test
    void canRetrieveTheHandlerForAGivenMethod() {
        Route route = new Route();
        Handler expectedHandler = ((request, response) -> {});
        route.addMethodHandler("GET", expectedHandler);

        assertEquals(expectedHandler, route.getMethodHandler("GET"));
    }
}
