package http.server;

import java.io.IOException;
import java.net.Socket;

public interface Server {
    void start(int port) throws IOException;
    Socket accept() throws IOException;
}
