package heartbeat.project.node.util;

import heartbeat.project.commons.utils.AppUtils;

import java.io.*;
import java.util.Properties;

/**
 * User: luc  | Date: 8/4/13  |  Time: 7:00 PM
 */
public class NodeAppUtil extends AppUtils {
    public static String ECHO_MSG = "echo";


    public static String getProperty(String propName){
        if( properties == null )
            initProperties();

        return properties.getProperty(propName);
    }


    private static Properties properties;
    private static void initProperties(){
        if( properties == null )
            properties = new Properties();

        try {
            properties.load(NodeAppUtil.class.getResourceAsStream("nodeInfoConfigurations.properties"));
//            new FileInputStream("nodeInfoConfigurations.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
