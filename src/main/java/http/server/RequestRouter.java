package http.server;

import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.NotFoundHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestRouter {
    private Socket socket;
    private Map<String, Map<String, Handler>> handlers;
    private BufferedReader in;
    private OutputStream out;

    public RequestRouter(Socket socket, Map<String, Map<String, Handler>> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    public void routeRequest() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();
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

    private boolean pathExists(Map<String, Handler> pathHandlers) {
        return pathHandlers != null;
    }

    private boolean methodExistsForPath(Map<String, Handler> pathHandlers, String method) {
        return pathHandlers.containsKey(method);
    }
}
