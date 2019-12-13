package http.server;

import http.server.controller.Controller;

import java.io.IOException;
import java.net.ServerSocket;

public class App {
    public static void main(String[] args) {
        String portNumber = args.length > 0 ? args[0] : "5000";
        try {
            RequestRouter router = Controller.routerSetup();
            Server server = new Server(new ServerSocket(Integer.parseInt(portNumber)), router);
            server.start();
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }
}
