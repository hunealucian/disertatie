import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * User: luc  | Date: 8/6/13  |  Time: 8:04 PM
 */
public class Test extends ObjectOutputStream {

    public Test(OutputStream out) throws IOException {
        super(out);
    }


}
