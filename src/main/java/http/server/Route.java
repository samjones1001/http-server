package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.OptionsHandler;

import java.util.HashMap;
import java.util.Map;

public class Route {
    private Map<String, Handler> methodHandlers = new HashMap<>();

    public Map<String, Handler> getMethodHandlers() {
        return methodHandlers;
    }

    public Handler getMethodHandler(String methodVerb) {
        if (methodHandlers.containsKey(methodVerb)) {
            return methodHandlers.get(methodVerb);
        } else {
            return new MethodNotAllowedHandler(methodHandlers);
        }
    }

    public void addMethodHandler(String methodVerb, Handler handler) {
        methodHandlers.putIfAbsent(methodVerb, handler);
        addDefaultMethods();
    }

    private void addDefaultMethods() {
        methodHandlers.putIfAbsent("HEAD", new HeadHandler());
        methodHandlers.put("OPTIONS", new OptionsHandler(methodHandlers));
    }
}
