package http.server;

import http.server.handlers.NotFoundHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private Map<String, Route> routes = new HashMap<>();

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public void addRoute(String path, String method, Handler handler) {
        Route route = routes.computeIfAbsent(path, (k) -> new Route());
        route.addMethodHandler(method, handler);
    }

    public void routeRequest(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        OutputStream out = client.getOutputStream();
        RequestParser rp = new RequestParser(in);
        Request request = rp.parse();
        Response response = new Response(out);
        Handler handler = retrieveHandler(request);
        handler.setResponseValues(request, response);
        response.send();
    }

    public Handler retrieveHandler(Request request) {
        Route route = routes.get(request.getPath());
        return routeExists(route) ? route.getMethodHandler(request.getMethod()) : NotFoundHandler.getHandler();
    }

    private boolean routeExists(Route route) {
        return route != null;
    }
}
