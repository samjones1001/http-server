package http.server.server.handlers;

import http.server.server.Handler;

import java.util.*;

public interface MethodNotAllowedHandler {
    static Handler getHandler(Set<String> allowedMethods) {
        return (((request, response) -> {
            response.setStatus(405, "Method Not Allowed");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", allowedMethodsHeaderValue(allowedMethods));
            response.addBody("");
        }));
    }

    private static String allowedMethodsHeaderValue(Set<String> allowedMethods) {
        return String.join(", ", buildAllowedMethodsList(allowedMethods));
    }

    private static ArrayList<String> buildAllowedMethodsList(Set<String> allowedMethods) {
        ArrayList<String> allowedMethodsList = new ArrayList<>(allowedMethods);
        Collections.sort(allowedMethodsList);
        return allowedMethodsList;
    }
}
