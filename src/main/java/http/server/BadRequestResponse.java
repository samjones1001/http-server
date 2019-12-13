package http.server;

import java.io.OutputStream;

public class BadRequestResponse extends Response {
    public BadRequestResponse(OutputStream out) {
        super(out);
        setStatus(400, "Bad Request");
    }
}
