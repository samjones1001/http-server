package http.server.handlers;

import http.server.Handler;
import http.server.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MethodNotAllowedHandler implements Handler {
    private final Map<String, Handler> methodHandlers;

    public MethodNotAllowedHandler(Map<String, Handler> methodHandlers) {
        this.methodHandlers = methodHandlers;
    }

    public void handle(Response response) {
        response.setStatusCode(405, "Method Not Allowed");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Allow", allowedMethods());
        response.addBody("");
    }

    private String allowedMethods() {
        ArrayList<String> allowedMethods = new ArrayList<>() {{
            add("HEAD");
            add("OPTIONS");
        }};

        for (String method: methodHandlers.keySet()) {
            if (!allowedMethods.contains(method)) {
                allowedMethods.add(method);
            }
        }

        Collections.sort(allowedMethods);
        return String.join(", ", allowedMethods);
    }
}
