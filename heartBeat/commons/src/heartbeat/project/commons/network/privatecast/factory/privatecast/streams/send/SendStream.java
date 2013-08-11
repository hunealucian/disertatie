package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send;

import heartbeat.project.commons.network.privatecast.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * User: luc  | Date: 8/9/13  |  Time: 3:59 PM
 */
public abstract class SendStream extends ObjectOutputStream {
    protected Message message;

    protected SendStream(OutputStream out) throws IOException {
        super(out);
    }

    public abstract void push() throws Exception;

    public void setMessage(Message msg) {
        this.message = msg;
    }
}
