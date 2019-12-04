package http.server;

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
            handler.handle(response);
            response.send();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public Handler retrieveHandler(Request request) {
        Map<String, Handler> pathHandlers = handlers.get(request.getPath());

        if (pathHandlers != null) {
            return pathHandlers.containsKey(request.getMethod()) ?
                    pathHandlers.get(request.getMethod()) :
                    handlers.get("err").get("err");
        } else {
            return handlers.get("err").get("err");
        }
    }
}
