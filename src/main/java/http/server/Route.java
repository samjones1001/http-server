package http.server;

import http.server.handlers.HeadHandler;
import http.server.handlers.MethodNotAllowedHandler;
import http.server.handlers.OptionsHandler;

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
                methodHandlers.get(methodVerb) : new MethodNotAllowedHandler(methodHandlers);
    }

    public void addMethodHandler(String methodVerb, Handler handler) {
        methodHandlers.putIfAbsent(methodVerb, handler);
        addDefaultMethods();
    }

    private void addDefaultMethods() {
        methodHandlers.putIfAbsent(headString, new HeadHandler());
        methodHandlers.put(optionsString, new OptionsHandler(methodHandlers));
    }
}
