package http.server;

import java.util.Map;

public class RequestRouter {
    private Map<String, Map<String, Handler>> handlers;

    public RequestRouter(Map<String, Map<String, Handler>> handlers) {
        this.handlers = handlers;
    }

    public Handler retrieveHandler(Request request) {
        Map<String, Handler> methodHandlers = handlers.get(request.getMethod());
        for (String path : methodHandlers.keySet()) {
            if (path.equals(request.getPath())) {
                return methodHandlers.get(path);
            }
        }
        return new NotFoundHandler();
    }
}
