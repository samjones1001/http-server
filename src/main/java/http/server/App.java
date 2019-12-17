package http.server;

import http.server.routing.setup.RouteSetup;
import http.server.server.RequestRouter;
import http.server.server.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class App {
    private static final int defaultPortNum = 5000;

    public static void main(String[] args) {
        int portNumber = args.length > 0 ? Integer.parseInt(args[0]) : defaultPortNum;
        try {
            RequestRouter router = RouteSetup.routerSetup();
            Server server = new Server(new ServerSocket(portNumber), router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
