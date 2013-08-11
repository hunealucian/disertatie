package heartbeat.project.commons.utils;

import java.io.*;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:21 PM
 */
public class AppUtils {

    public  static int secondsToDead = 30000;  //number of second until one commons is considered down
    public static int TIME_OUT = 10000;


    public static class SERIALIZABLE {
        public static synchronized byte[] serializeThis(Object object) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;

            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(object);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new IOException(e);
            } finally {
                if (out != null)
                    out.close();
                bos.close();
            }
        }

        public static synchronized Object deserializeThis(byte[] data) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInput in = null;
            try {
                in = new ObjectInputStream(bis);
                return  in.readObject();
            } finally {
                if (in != null)
                    in.close();
                bis.close();
            }


        }
    }

}
