package http.server.handlers;

import http.server.Handler;
import http.server.Response;

import java.util.*;

public class OptionsHandler implements Handler {
    private final Map<String, Handler> methodHandlers;

    public OptionsHandler(Map<String, Handler> methodHandlers) {
        this.methodHandlers = methodHandlers;
    }

    public void setResponseValues(Response response) {
        response.setStatusCode(200, "No Content");
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Allow", allowedMethodsHeaderValue());
        response.addBody("");
    }

    private String allowedMethodsHeaderValue() {
        Set<String> allowedMethods = new HashSet<>() {{
            add("HEAD");
            add("OPTIONS");
        }};
        return String.join(", ", buildAllowedMethodsList(allowedMethods));
    }

    private ArrayList<String> buildAllowedMethodsList(Set<String> allowedMethods) {
        for (String method: methodHandlers.keySet()) {
            allowedMethods.add(method);
        }
        ArrayList<String> allowedMethodsList = new ArrayList<>(allowedMethods);
        Collections.sort(allowedMethodsList);
        return allowedMethodsList;
    }
}
