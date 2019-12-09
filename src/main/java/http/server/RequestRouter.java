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
    private BufferedReader in;
    private OutputStream out;

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public void addRoute(String path, String method, Handler handler) {
        Route route = new Route();
        routes.putIfAbsent(path, route);
        route.addMethodHandler(method, handler);
    }

    public void routeRequest(Socket client) {
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = client.getOutputStream();
            Request request = new Request(in);
            Response response = new Response(out);
            Handler handler = retrieveHandler(request);
            handler.setResponseValues(request, response);
            response.send();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public Handler retrieveHandler(Request request) {
        Route route = routes.get(request.getPath());
        if (routeExists(route)) {
            return route.getMethodHandler(request.getMethod());
        }
        return new NotFoundHandler();
    }

    private boolean routeExists(Route route) {
        return route != null;
    }
}
