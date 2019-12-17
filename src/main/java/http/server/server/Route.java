package http.server.server;

import http.server.server.handlers.HeadHandler;
import http.server.server.handlers.MethodNotAllowedHandler;
import http.server.server.handlers.OptionsHandler;

import java.util.HashMap;
import java.util.Map;

public class Route {
    private static final String headString = "HEAD";
    private static final String optionsString = "OPTIONS";

    private Map<String, Handler> methodHandlers = new HashMap<>();

    public Map<String, Handler> getMethodHandlers() {
        return methodHandlers;
    }

    public Handler getMethodHandler(String methodVerb) {
        return methodHandlers.containsKey(methodVerb) ?
                methodHandlers.get(methodVerb) : MethodNotAllowedHandler.getHandler(methodHandlers.keySet());
    }

    public void addMethodHandler(String methodVerb, Handler handler) {
        methodHandlers.putIfAbsent(methodVerb, handler);
        addDefaultMethods();
    }

    private void addDefaultMethods() {
        methodHandlers.putIfAbsent(headString, HeadHandler.getHandler());
        methodHandlers.put(optionsString, OptionsHandler.getHandler(methodHandlers.keySet()));
    }
}
