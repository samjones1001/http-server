package http.server.mocks;

import http.server.Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class MockServerSocket implements Server {
    private MockSocket mockSocket;
    private int port;

    public MockServerSocket() throws IOException {
        this.mockSocket = new MockSocket(new ByteArrayInputStream("".getBytes(Charset.forName("UTF-8"))), new ByteArrayOutputStream());
    }

    public MockServerSocket(MockSocket mockSocket) {
        this.mockSocket = mockSocket;
    }

    @Override
    public void start(int port) {
        this.port = port;
    }

    @Override
    public MockSocket accept() throws IOException {
        MockSocket socket = this.mockSocket;
        this.mockSocket = null;
        return socket;
//        return this.mockSocket;
    }
}
