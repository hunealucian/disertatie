package project.manager.util;

import heartbeat.project.commons.utils.AppUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:39 PM
 */
public class ManagerAppUtil extends AppUtils {

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
            properties.load(ManagerAppUtil.class.getResourceAsStream("managerInfoConfigurations.properties"));
//            new FileInputStream("nodeInfoConfigurations.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
