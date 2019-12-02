package http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements Server {
    ServerSocket serverSocket;

    @Override
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }
}
