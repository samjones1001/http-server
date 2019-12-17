package http.server;

import http.server.routing.setup.RouteSetup;
import http.server.server.RequestRouter;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    @Test
    void CreatesARouterWithTheExpectedRoutes() {
        RequestRouter router = RouteSetup.routerSetup();
        Set<String> routes = router.getRoutes().keySet();

        assertTrue(routes.contains("/simple_get"));
        assertTrue(routes.contains("/get_with_body"));
        assertTrue(routes.contains("/method_options"));
        assertTrue(routes.contains("/method_options2"));
        assertTrue(routes.contains("/echo_body"));
        assertTrue(routes.contains("/redirect"));
    }
}
