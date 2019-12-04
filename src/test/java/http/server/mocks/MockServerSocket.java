package http.server.mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.Charset;

public class MockServerSocket extends ServerSocket {
    private MockSocket mockSocket;

    public MockServerSocket() throws IOException {
        this.mockSocket = new MockSocket(new ByteArrayInputStream("".getBytes(Charset.forName("UTF-8"))), new ByteArrayOutputStream());
    }

    public MockServerSocket(MockSocket mockSocket) throws IOException {
        this.mockSocket = mockSocket;
    }

    @Override
    public MockSocket accept() throws IOException {
        MockSocket socket = this.mockSocket;
        this.mockSocket = null;
        return socket;
    }
}
