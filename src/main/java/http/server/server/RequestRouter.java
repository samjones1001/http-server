package http.server.server;

import http.server.server.exceptions.ParseException;
import http.server.server.request.Request;
import http.server.server.request.RequestParser;
import http.server.server.response.BadRequestResponse;
import http.server.server.response.Response;
import http.server.server.response.ServerErrorResponse;
import http.server.server.handlers.NotFoundHandler;

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
        OutputStream out = client.getOutputStream();
        Response response = new Response(out);

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Request request = new RequestParser(in).parse();
            Handler handler = retrieveHandler(request);
            handler.setResponseValues(request, response);
        } catch (ParseException err) {
            response = new BadRequestResponse(out);
        } catch (Error err) {
            response = new ServerErrorResponse(out);
        }

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
