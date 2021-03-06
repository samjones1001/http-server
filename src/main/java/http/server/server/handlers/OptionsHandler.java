package http.server.server.handlers;

import http.server.server.Handler;

import java.util.*;

public interface OptionsHandler {
    static Handler getHandler(Set<String> availableMethods) {
        return (((request, response) -> {
            response.setStatus(200, "No Content");
            response.addHeader("Content-Type", "text/html");
            response.addHeader("Allow", allowedMethodsHeaderValue(availableMethods));
            response.addBody("".getBytes());
        }));
    }

    private static String allowedMethodsHeaderValue(Set<String> availableMethods) {
        return String.join(", ", buildAllowedMethodsList(availableMethods));
    }

    private static ArrayList<String> buildAllowedMethodsList(Set<String> availableMethods) {
        ArrayList<String> allowedMethodsList = new ArrayList<>(availableMethods);
        Collections.sort(allowedMethodsList);
        return allowedMethodsList;
    }
}
