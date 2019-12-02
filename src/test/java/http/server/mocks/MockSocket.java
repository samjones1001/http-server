package http.server.mocks;

import java.io.*;
import java.net.Socket;

public class MockSocket extends Socket {
    private final ByteArrayInputStream in;
    private final ByteArrayOutputStream out;

    public MockSocket(ByteArrayInputStream in, ByteArrayOutputStream out) throws IOException {
        this.in = in;
        this.out = out;
    }

    @Override
    public ByteArrayInputStream getInputStream() {
        return in;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        return out;
    }
}
