package http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestRouter implements Runnable {
    private Map<String, Map<String, Handler>> handlers;
    private Socket socket;
    private BufferedReader in;
    private OutputStream out;

    public RequestRouter(Socket socket, Map<String, Map<String, Handler>> handlers) {
        this.handlers = handlers;
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = socket.getOutputStream();
            Request request = new Request(in);
            request.parse();
            Response response = new Response(out);
            Handler handler = retrieveHandler(request);
            handler.handle(response);
            response.send();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public Handler retrieveHandler(Request request) {
        Map<String, Handler> methodHandlers = handlers.get(request.getMethod());
        for (String path : methodHandlers.keySet()) {
            if (path.equals(request.getPath())) {
                return methodHandlers.get(path);
            }
        }
        return handlers.get("ERR").get("/not_found");
    }
}
