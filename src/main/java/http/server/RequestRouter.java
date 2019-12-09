package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.NotFoundHandler;
import http.server.handlers.OptionsHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestRouter {
    private Map<String, Map<String, Handler>> handlers = new HashMap<>();
    private BufferedReader in;
    private OutputStream out;

    public Map<String, Map<String, Handler>> getHandlers() {
        return handlers;
    }

    public void addHandler(String path, String method, Handler handler) {
        Map<String, Handler> pathHandlers = handlers.computeIfAbsent(path, key -> new HashMap<>());
        pathHandlers.put(method, handler);
        addDefaultHandlers(pathHandlers);
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
        Map<String, Handler> pathHandlers = handlers.get(request.getPath());

        if (pathExists(pathHandlers)) {
            return methodExistsForPath(pathHandlers, request.getMethod()) ?
                    pathHandlers.get(request.getMethod()) :
                    new MethodNotAllowedHandler(pathHandlers);
        } else {
            return new NotFoundHandler();
        }
    }

    private void addDefaultHandlers(Map<String, Handler> methodHandlers) {
        if (!methodHandlers.containsKey("HEAD")) {
            methodHandlers.put("HEAD", new HeadHandler());
        }
        if (!methodHandlers.containsKey("OPTIONS")) {
            methodHandlers.put("OPTIONS", new OptionsHandler(methodHandlers));
        }
    }

    private boolean pathExists(Map<String, Handler> pathHandlers) {
        return pathHandlers != null;
    }

    private boolean methodExistsForPath(Map<String, Handler> pathHandlers, String method) {
        return pathHandlers.containsKey(method);
    }
}
