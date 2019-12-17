package http.server.server.response;

import java.io.OutputStream;

public class ServerErrorResponse extends Response {
    public ServerErrorResponse(OutputStream out) {
        super(out);
        setStatus(500, "Internal Server Error");
    }
}